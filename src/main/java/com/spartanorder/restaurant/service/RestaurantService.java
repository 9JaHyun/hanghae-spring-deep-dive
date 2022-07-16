package com.spartanorder.restaurant.service;

import com.spartanorder.restaurant.Restaurant;
import com.spartanorder.restaurant.dto.RestaurantDto;
import com.spartanorder.restaurant.exception.BadDeliveryFeeException;
import com.spartanorder.restaurant.exception.BadOrderPriceException;
import com.spartanorder.restaurant.repository.RestaurantRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public RestaurantDto registerRestaurant(RestaurantDto dto) {
        validateMinOrderPrice(dto.minOrderPrice());
        validateDeliveryFee(dto.deliveryFee());

        Restaurant saveEntity = restaurantRepository.save(
              Restaurant.of(dto.name(), dto.minOrderPrice(), dto.deliveryFee()));

        return new RestaurantDto(saveEntity.getId(), saveEntity.getName(),
              saveEntity.getMinOrderPrice(), saveEntity.getDeliveryFee());
    }

    private void validateDeliveryFee(int deliveryFee) {
        if (!(deliveryFee % 500 == 0)) {
            throw new BadDeliveryFeeException("배달비는 100원 단위로 만들어주세요!");
        }
        if (deliveryFee > 10000 || deliveryFee < 0) {
            throw new BadDeliveryFeeException("최소 주문 금액은 0원 ~ 10,000원 이하로 정해주세요!");
        }
    }

    private void validateMinOrderPrice(int minOrderPrice) {
        if (!(minOrderPrice % 100 == 0)) {
            throw new BadOrderPriceException("가격은 100원 단위로 만들어주세요!");
        }

        if (minOrderPrice > 100000 || minOrderPrice < 1000) {
            throw new BadOrderPriceException("가격은 1000원 ~ 100,000원 사이로 정해주세요!");
        }
    }

    @Transactional(readOnly = true)
    public List<RestaurantDto> showRestaurantsAll() {
        return restaurantRepository.findAll()
              .stream()
              .map(restaurant -> new RestaurantDto(restaurant.getId(), restaurant.getName(),
                    restaurant.getMinOrderPrice(), restaurant.getDeliveryFee()))
              .collect(Collectors.toList());
    }
}

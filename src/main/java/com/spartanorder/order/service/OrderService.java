package com.spartanorder.order.service;

import com.spartanorder.menu.Menu;
import com.spartanorder.menu.repository.MenuRepository;
import com.spartanorder.order.domain.Order;
import com.spartanorder.order.domain.OrderMenu;
import com.spartanorder.order.dto.OrderDto;
import com.spartanorder.order.dto.OrderMenuDto;
import com.spartanorder.order.dto.OrderMenuResponseDto;
import com.spartanorder.order.dto.OrderResponseDto;
import com.spartanorder.order.repository.OrderRepository;
import com.spartanorder.restaurant.Restaurant;
import com.spartanorder.restaurant.repository.RestaurantRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    // TODO 뭔가 더 좋은 방법이 있을 것 같은데..
    //      1. 의존성 역전을 통해 의존성 사이클 고치기
    //      2. Spring Event 사용하기
    public OrderService(OrderRepository orderRepository,
          MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public OrderResponseDto takeOrder(OrderDto orderDto) {
        Set<OrderMenu> orderMenuList = extractOrderMenuList(orderDto);

        Restaurant restaurant = restaurantRepository.findById(orderDto.restaurantId())
              .orElseThrow(() -> new IllegalArgumentException("없는 가게입니다."));

        long totalPrice = calcTotalPrice(orderMenuList);

        validateMinOrderPrice(totalPrice, restaurant);

        Order order = Order.createOrder(orderDto.restaurantId(), orderMenuList);

        orderRepository.save(order);

        // 쿼리를 꼭 같이 해줘야 하나??
        Set<OrderMenuResponseDto> orderMenuResponseDtoList =
              createOrderMenuResponseDtoList(orderMenuList);

        return new OrderResponseDto(restaurant.getName(),
              new HashSet<>(orderMenuResponseDtoList),
              restaurant.getDeliveryFee(),
              totalPrice + restaurant.getDeliveryFee());
    }

    private Set<OrderMenu> extractOrderMenuList(OrderDto orderDto) {
        return orderDto.orderMenus().stream()
              .map(this::createOrderMenu)
              .collect(Collectors.toSet());
    }

    private Set<OrderMenuResponseDto> createOrderMenuResponseDtoList(Set<OrderMenu> orderMenuList) {
        return orderMenuList
              .stream()
              .map(orderMenu -> new OrderMenuResponseDto(orderMenu.getMenuName(),
                    orderMenu.getQuantity(),
                    (long) orderMenu.getPrice() * orderMenu.getQuantity()))
              .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> showOrders() {
        List<Order> orderEntityList = orderRepository.findAll();

        return orderEntityList.stream()
              .map(entity -> {
                  Restaurant restaurant = restaurantRepository.findById(entity.getRestaurantId())
                        .orElseThrow(() -> new IllegalArgumentException("잘못된 식당 ID입니다"));

                  Set<OrderMenuResponseDto> menus = createOrderMenuResponseDtoList(
                        entity.getOrderMenus());

                  return new OrderResponseDto(restaurant.getName(), menus,
                        restaurant.getDeliveryFee(),
                        entity.calcOrderPrice() + restaurant.getDeliveryFee());
              })
              .collect(Collectors.toList());
    }

    private long calcTotalPrice(Set<OrderMenu> orderMenuList) {
        return orderMenuList.stream()
              .map(OrderMenu::calcTotalPrice)
              .reduce(0L, Long::sum);
    }

    private OrderMenu createOrderMenu(OrderMenuDto menuDto) {
        Menu menu = menuRepository.findById(menuDto.menuId())
              .orElseThrow(() -> new IllegalArgumentException("없는 메뉴입니다."));

        validateQuantity(menuDto);

        return OrderMenu.of(menu.getId(), menu.getName(), menu.getPrice(), menuDto.quantity());
    }

    private void validateMinOrderPrice(long totalPrice, Restaurant restaurant) {
        if (totalPrice < restaurant.getMinOrderPrice()) {
            throw new IllegalArgumentException("가게의 최소 주문 금액을 넘겨야 합니다.");
        }
    }

    private void validateQuantity(OrderMenuDto menuDto) {
        if (menuDto.quantity() > 100 || menuDto.quantity() < 1) {
            throw new IllegalArgumentException("주문 수량은 1~100 개만 가능합니다.");
        }
    }
}

package com.spartanorder.restaurant.controller;

import com.spartanorder.restaurant.dto.RestaurantDto;
import com.spartanorder.restaurant.service.RestaurantService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 선언 => 이 url로 접속을하면 이 일을 할거야
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @PostMapping("/restaurant/register")
    public ResponseEntity<RestaurantDto> register(@Valid @RequestBody RestaurantDto requestDto) {

        RestaurantDto result = restaurantService.registerRestaurant(requestDto); // 음식점 등록

        return ResponseEntity.status(HttpStatus.CREATED)
              .contentType(MediaType.APPLICATION_JSON)
              .body(result);
    }

    // TODO: 배열의 경우 Wrapper 클래스를 통해 한번 더 감싸는게
    //       프론트에서 가져오기 쉬울 것 같은데 추가해보자
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDto>> showRestaurants() {
        List<RestaurantDto> result = restaurantService.showRestaurantsAll();

        return ResponseEntity.status(HttpStatus.OK)
              .contentType(MediaType.APPLICATION_JSON)
              .body(result);
    }

    @GetMapping("/restaurant/deleteAll")
    public void deleteAll() {
        restaurantService.deleteAll();
    }
}

package com.spartanorder.menu.controller;

import com.spartanorder.menu.dto.MenuDto;
import com.spartanorder.menu.dto.MenuResponseDto;
import com.spartanorder.menu.service.MenuService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/restaurant/{restaurantId}/food/register")
    public ResponseEntity<Void> registerMenu(@PathVariable Long restaurantId,
          @RequestBody List<MenuDto> menuDto) {
        menuService.registerMenu(restaurantId, menuDto);

        return ResponseEntity.status(HttpStatus.CREATED)
              .body(null);
    }

    @GetMapping("/restaurant/{restaurantId}/foods")
    public ResponseEntity<List<MenuResponseDto>> showMenuInRestaurant(@PathVariable Long restaurantId) {
        List<MenuResponseDto> result = menuService.showMenuByRestaurantId(restaurantId);

        return ResponseEntity
              .status(HttpStatus.OK)
              .contentType(MediaType.APPLICATION_JSON)
              .body(result);
    }
}

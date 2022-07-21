package com.spartanorder.menu.service;

import com.spartanorder.menu.Menu;
import com.spartanorder.menu.dto.MenuDto;
import com.spartanorder.menu.dto.MenuResponseDto;
import com.spartanorder.menu.exception.BadPriceException;
import com.spartanorder.menu.exception.DuplicateMenuName;
import com.spartanorder.menu.repository.MenuRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    public void registerMenu(Long restaurantId, List<MenuDto> menuDto) {
        menuDto.forEach(dto -> {
            validateMenuName(restaurantId, dto.name());
            validatePrice(dto.price());
            menuRepository.save(Menu.of(dto.name(), dto.price(), restaurantId));
        });
    }

    private void validateMenuName(Long restaurantId, String name) {
        boolean checkDuplicateMenuName =
              menuRepository.findByRestaurantIdAndName(restaurantId, name).isPresent();

        if (checkDuplicateMenuName) {
            throw new DuplicateMenuName("이미 존재하는 메뉴이름입니다.");
        }
    }

    private void validatePrice(int price) {
        if (!(price % 100 == 0)) {
            throw new BadPriceException("가격은 100원 단위로 만들어주세요!");
        }

        if (price > 1000000 || price < 100) {
            throw new BadPriceException("가격은 100원 ~ 1,000,000원 사이로 정해주세요!");
        }
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto> showMenuByRestaurantId(Long restaurantId) {
        return menuRepository.findByRestaurantId(restaurantId)
              .stream()
              .map(menu -> new MenuResponseDto(menu.getId(), menu.getName(), menu.getPrice()))
              .collect(Collectors.toList());
    }

    public void deleteAll() {
        menuRepository.deleteAll();
    }
}

package com.spartanorder.menu.repository;

import com.spartanorder.menu.Menu;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByRestaurantId(Long restaurantId);

    Optional<Menu> findByRestaurantIdAndName(Long restaurantId, String name);
}

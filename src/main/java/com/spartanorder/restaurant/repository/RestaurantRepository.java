package com.spartanorder.restaurant.repository;

import com.spartanorder.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

// Restaurant라는 Entity만 받을 수 있는 JPARepository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}

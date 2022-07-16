package com.spartanorder.order.repository;

import com.spartanorder.order.domain.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {

}

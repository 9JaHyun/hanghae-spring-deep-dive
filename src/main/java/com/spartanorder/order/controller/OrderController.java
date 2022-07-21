package com.spartanorder.order.controller;

import com.spartanorder.order.dto.OrderDto;
import com.spartanorder.order.dto.OrderResponseDto;
import com.spartanorder.order.service.OrderService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/request")
    public ResponseEntity<OrderResponseDto> takeOrder(@RequestBody OrderDto orderDto) {
        OrderResponseDto orderResponseDto = orderService.takeOrder(orderDto);

        return ResponseEntity
              .status(HttpStatus.CREATED)
              .contentType(MediaType.APPLICATION_JSON)
              .body(orderResponseDto);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> showOrders() {
        return ResponseEntity
              .status(HttpStatus.OK)
              .contentType(MediaType.APPLICATION_JSON)
              .body(orderService.showOrders());
    }

    @GetMapping("/orders/deleteAll")
    public void deleteAll() {
        orderService.deleteAll();
    }
}

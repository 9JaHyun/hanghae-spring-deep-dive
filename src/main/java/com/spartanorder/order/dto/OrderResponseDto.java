package com.spartanorder.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;

public record OrderResponseDto(
      @JsonProperty("restaurantName") @NotNull @NotNull String restaurantName,
      @JsonProperty("foods") @NotNull Set<OrderMenuResponseDto> orderMenus,
      @JsonProperty("deliveryFee") int deliveryFee,
      @JsonProperty("totalPrice") long totalPrice) implements Serializable {

    @Serial
    private static final long serialVersionUID = 0L;

    public OrderResponseDto(
          @NotNull @JsonProperty("restaurantName") @NotNull String restaurantName,
          @NotNull @JsonProperty("foods") Set<OrderMenuResponseDto> orderMenus,
          @JsonProperty("deliveryFee") int deliveryFee,
          @JsonProperty("totalPrice") long totalPrice) {
        this.restaurantName = restaurantName;
        this.orderMenus = orderMenus;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (OrderResponseDto) obj;
        return Objects.equals(this.restaurantName, that.restaurantName) &&
              Objects.equals(this.orderMenus, that.orderMenus) &&
              this.deliveryFee == that.deliveryFee &&
              this.totalPrice == that.totalPrice;
    }

    @Override
    public String toString() {
        return "OrderResponseDto[" +
              "restaurantName=" + restaurantName + ", " +
              "orderMenus=" + orderMenus + ", " +
              "deliveryFee=" + deliveryFee + ", " +
              "totalPrice=" + totalPrice + ']';
    }


}

package com.spartanorder.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;

public record OrderDto(@JsonProperty("id") Long id,
                       @JsonProperty("restaurantId") @NotNull @NotNull Long restaurantId,
                       @JsonProperty("foods") @NotNull Set<OrderMenuDto> orderMenus) implements
      Serializable {

    @Serial
    private static final long serialVersionUID = 0L;

    public OrderDto(
          @JsonProperty("id") Long id,
          @NotNull @JsonProperty("restaurantId") @NotNull Long restaurantId,
          @NotNull @JsonProperty("foods") Set<OrderMenuDto> orderMenus) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.orderMenus = orderMenus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OrderDto orderDto)) {
            return false;
        }
        return Objects.equals(this.id, orderDto.id);
    }

    @Override
    public String toString() {
        return "OrderDto[" +
              "id=" + id + ", " +
              "restaurantId=" + restaurantId + ", " +
              "orderMenus=" + orderMenus + ']';
    }


}

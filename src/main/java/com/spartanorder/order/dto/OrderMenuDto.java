package com.spartanorder.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public record OrderMenuDto(@JsonProperty("id") @NotNull Long menuId,
                           @JsonProperty("quantity") @NotNull int quantity) implements
      Serializable {

    @Serial
    private static final long serialVersionUID = 0L;

    public OrderMenuDto(
          @JsonProperty("id") @NotNull Long menuId,
          @JsonProperty("quantity") @NotNull int quantity) {
        this.menuId = menuId;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (OrderMenuDto) obj;
        return Objects.equals(this.menuId, that.menuId) &&
              this.quantity == that.quantity;
    }

    @Override
    public String toString() {
        return "OrderMenuDto[" +
              "menuId=" + menuId + ", " +
              "quantity=" + quantity + ']';
    }


}

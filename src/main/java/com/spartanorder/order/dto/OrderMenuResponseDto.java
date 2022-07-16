package com.spartanorder.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record OrderMenuResponseDto(@JsonProperty("name") @NotBlank @NotNull String name,
                                   @JsonProperty("quantity") @NotNull @NotNull int quantity,
                                   @JsonProperty("price") @NotNull @NotNull long price) implements
      Serializable {

    @Serial
    private static final long serialVersionUID = 0L;

    public OrderMenuResponseDto(
          @NotBlank @JsonProperty("name") @NotNull String name,
          @NotNull @JsonProperty("quantity") @NotNull int quantity,
          @NotNull @JsonProperty("price") @NotNull long price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (OrderMenuResponseDto) obj;
        return Objects.equals(this.name, that.name) &&
              this.quantity == that.quantity &&
              this.price == that.price;
    }

    @Override
    public String toString() {
        return "OrderMenuResponseDto[" +
              "name=" + name + ", " +
              "quantity=" + quantity + ", " +
              "price=" + price + ']';
    }


}

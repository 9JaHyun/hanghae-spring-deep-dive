package com.spartanorder.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record RestaurantDto(@JsonProperty("id") Long id,
                            @JsonProperty("name") @NotBlank String name,
                            @JsonProperty("minOrderPrice") @NotNull int minOrderPrice,
                            @JsonProperty("deliveryFee") @NotNull int deliveryFee) implements
      Serializable {

    @Serial
    private static final long serialVersionUID = 0L;

    public RestaurantDto(
          @JsonProperty("id") Long id,
          @NotBlank @JsonProperty("name") String name,
          @NotNull @JsonProperty("minOrderPrice") int minOrderPrice,
          @NotNull @JsonProperty("deliveryFee") int deliveryFee) {
        this.id = id;
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.deliveryFee = deliveryFee;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (RestaurantDto) obj;
        return Objects.equals(this.id, that.id) &&
              Objects.equals(this.name, that.name) &&
              this.minOrderPrice == that.minOrderPrice &&
              this.deliveryFee == that.deliveryFee;
    }

    @Override
    public String toString() {
        return "RestaurantDto[" +
              "id=" + id + ", " +
              "name=" + name + ", " +
              "minOrderPrice=" + minOrderPrice + ", " +
              "deliveryFee=" + deliveryFee + ']';
    }

}

package com.spartanorder.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public record MenuResponseDto(@JsonProperty("id") @NotNull Long id,
                              @JsonProperty("name") @NotNull String name,
                              @JsonProperty("price") @NotNull int price) implements Serializable {

    @Serial
    private static final long serialVersionUID = 0L;

    public MenuResponseDto(
          @NotNull @JsonProperty("id") Long id,
          @NotNull @JsonProperty("name") String name,
          @NotNull @JsonProperty("price") int price) {
        this.id = id;
        this.name = name;
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
        var that = (MenuResponseDto) obj;
        return Objects.equals(this.id, that.id) &&
              Objects.equals(this.name, that.name) &&
              this.price == that.price;
    }

    @Override
    public String toString() {
        return "MenuResponseDto[" +
              "id=" + id + ", " +
              "name=" + name + ", " +
              "price=" + price + ']';
    }


}

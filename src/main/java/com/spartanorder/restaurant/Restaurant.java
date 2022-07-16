package com.spartanorder.restaurant;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter
@DynamicUpdate
@Table(indexes = {
      @Index(columnList = "name")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int minOrderPrice;
    @Column(nullable = false)
    private int deliveryFee;

    @Column(updatable = false) @CreatedDate private LocalDateTime createdAt;
    @Column(updatable = false) @CreatedBy private String createdBy;
    @Column(insertable = false) @LastModifiedDate private LocalDateTime updatedAt;
    @Column(insertable = false) @LastModifiedBy private String updatedBy;

    protected Restaurant() {
    }

    private Restaurant(Long id, String name, int minOrderPrice, int deliveryFee) {
        this.id = id;
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.deliveryFee = deliveryFee;
    }

    public static Restaurant of(String name, int minOrderPrice, int deliveryFee) {
        return new Restaurant(null, name, minOrderPrice, deliveryFee);
    }

    public static Restaurant of(Long id, String name, int minOrderPrice, int deliveryFee) {
        return new Restaurant(id, name, minOrderPrice, deliveryFee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant restaurant)) {
            return false;
        }

        return id != null && Objects.equals(id, restaurant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeMinOrderPrice(int minOrderPrice) {
        this.minOrderPrice = minOrderPrice;
    }

    public void changeDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}

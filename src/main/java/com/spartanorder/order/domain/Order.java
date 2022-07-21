package com.spartanorder.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
@Entity
public class Order extends AbstractAggregateRoot<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderMenu> orderMenus = new HashSet<>();

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Setter
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(updatable = false) @CreatedDate private LocalDateTime createdAt;
    @Column(updatable = false) @CreatedBy private String createdBy;
    @Column(insertable = false) @LastModifiedDate private LocalDateTime updatedAt;
    @Column(insertable = false) @LastModifiedBy private String updatedBy;

    protected Order() {
    }

    private Order(Long restaurantId, Set<OrderMenu> orderMenus) {
        this.restaurantId = restaurantId;
        this.orderMenus = orderMenus;
    }

    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenus.add(orderMenu);
        orderMenu.setOrder(this);
    }

    public static Order createOrder(Long restaurantId, Set<OrderMenu> orderMenus) {
        Order order = new Order(restaurantId, orderMenus);

        for (OrderMenu orderMenu : orderMenus) {
            order.addOrderMenu(orderMenu);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        return order;
    }

    public void cancel() {
        if (orderStatus == OrderStatus.CANCEL) {
            throw new IllegalStateException("이미 취소된 주문입니다.");
        }

        this.setOrderStatus(OrderStatus.CANCEL);
    }

    public int calcOrderPrice() {
        int orderPrice = 0;
        for (OrderMenu menu : orderMenus) {
            orderPrice += menu.calcTotalPrice();
        }
        return orderPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order order)) {
            return false;
        }

        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

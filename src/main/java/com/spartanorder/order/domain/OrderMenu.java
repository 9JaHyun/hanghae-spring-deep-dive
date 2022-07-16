package com.spartanorder.order.domain;

import static javax.persistence.FetchType.LAZY;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class OrderMenu {

    /**
     * ID 매핑 방식 - 직접 넣어주기 setId(Long id) - DB에게 맡기기 -> @GeneratedValue - TABLE(worst) : ID만을 관리하는
     * 테이블을 따로 생성 - IDENTITY     : AUTO INCREMENT -> 어느 DB에는 있고 어느 DB에는 없음. - SEQUENCE     : DB
     * SEQUENCE 활용 => 거의 다 있음. - AUTO는 3중에 가장 어울리는 것을 선택.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    protected OrderMenu() {
    }

    private OrderMenu(Long menuId, String menuName, int price, int quantity) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.quantity = quantity;
    }

    public static OrderMenu of(Long menuId, String menuName, int price, int quantity) {
        return new OrderMenu(menuId, menuName, price, quantity);
    }

    public long calcTotalPrice() {
        return (long) price * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderMenu orderMenu)) {
            return false;
        }

        return id != null && Objects.equals(id, orderMenu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

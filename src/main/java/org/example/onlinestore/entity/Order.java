package org.example.onlinestore.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORDERS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", allocationSize = 1)
    @Column(name = "ORDER_ID")
    private int orderID;

    @Column(name = "ORDER_DATE")
    private LocalDateTime orderDate;

    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    @Column(name = "ORDER_TOTAL")
    private BigDecimal orderPrice;

    @Column(name = "ORDER_ADDRESS")
    private String orderAddress;

    @Column(name = "ORDER_PAYMENT")
    private String orderPayment;

    @ManyToOne
    @JoinColumn(name = "USER_USERID")
    private User user;

    public Order(int orderID) {
        this.orderID = orderID;
    }
}

package com.example.ubfactory.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "OrderItems")
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderSummary orderSummary;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    // getters and setters
}
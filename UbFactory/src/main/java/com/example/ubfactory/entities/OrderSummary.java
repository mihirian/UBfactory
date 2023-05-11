package com.example.ubfactory.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "OrderSummary")
public class OrderSummary extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "shipping_id", nullable = false)
    private Shipping shipping;

    @Column(name = "refund_status")
    private String refundStatus;

    @Column(name = "reason")
    private String reason;

    // getters and setters
}
package com.example.ubfactory.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "shipping")
public class Shipping extends BaseEntity {

    @Column(name = "charges", nullable = false)
    private BigDecimal charges;

    @OneToMany(mappedBy = "shipping")
    private Set<OrderSummary> orders;

    // getters and setters
}
package com.example.ubfactory.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "shipping")
public class Shipping extends BaseEntity {

    @Column(name = "charges", nullable = false)
    private BigDecimal charges;

    @OneToMany(mappedBy = "shipping")
    private Set<OrderSummary> orders;

    // getters and setters
}
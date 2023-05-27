package com.example.ubfactory.entities;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "remark")
    private String remark;

    @Column(name = "point")
    private int point;
}

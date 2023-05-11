package com.example.ubfactory.entities;

import javax.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "feedback", nullable = false)
    private String feedback;

    // Getters and Setters
}

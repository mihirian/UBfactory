package com.example.ubfactory.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "customer")
@Data
public class Customer extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "customer")
    private Set<Address> addresses;

    @OneToMany(mappedBy = "customer")
    private Set<OrderSummary> orderSummaries;

    @OneToMany(mappedBy = "customer")
    private Set<Feedback> feedbacks;

    // getters and setters
}

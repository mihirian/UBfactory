package com.example.ubfactory.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Table(name = "customer")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends BaseEntity {


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "owner_type", nullable = false)
    private String ownerType;
    private String streetAddress;
    private String town;
    private String state;
    private String pinCode;
    private Boolean isDeliverable;


    @OneToMany(mappedBy = "customer")
    private Set<Address> addresses;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<OrderSummary> orderSummaries;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<Feedback> feedbacks;

    @JsonIgnore
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;

    // getters and setters
}

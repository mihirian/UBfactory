package com.example.ubfactory.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "address")
public class Address extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "landmark", nullable = false)
    private String landmark;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "pincode", nullable = false)
    private Integer pincode;

}



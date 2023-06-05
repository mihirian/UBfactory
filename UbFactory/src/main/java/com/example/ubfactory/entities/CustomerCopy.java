package com.example.ubfactory.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Customer_Copy")
public class CustomerCopy extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "otp", nullable = false)
    private String otp;
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "owner_type", nullable = false)
    private String ownerType;
}

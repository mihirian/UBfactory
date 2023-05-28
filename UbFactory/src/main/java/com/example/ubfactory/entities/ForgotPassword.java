package com.example.ubfactory.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ForgotPassword extends BaseEntity {
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "otp", nullable = false)
    private String otp;
}

package com.example.ubfactory.objects;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CustomerObject {
    protected Integer id;
    @NotBlank
    private String firstName;
    private String lastName;
    @NotBlank
    private String mobile;
    @NotBlank(message = "please enter the email ")
    @Email(message = "Enter the valid email")
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;
    private String ownertype;

}

package com.example.ubfactory.objects;

import lombok.Data;

@Data
public class CustomerRequest {
    protected Integer id;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String password;
}

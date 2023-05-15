package com.example.ubfactory.objects;

import lombok.Data;

@Data
public class LoginRequest
{
    private String email;
    private String password;
    private String firstName;
}

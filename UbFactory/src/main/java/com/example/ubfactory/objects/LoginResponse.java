package com.example.ubfactory.objects;

import lombok.Data;

@Data
public class LoginResponse
{
    private String token;
    private String ownerType;
    private Long ownerId;
    private String message;
}

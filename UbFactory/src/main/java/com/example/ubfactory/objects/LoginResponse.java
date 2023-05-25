package com.example.ubfactory.objects;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String ownerType;
    private Long ownerId;
    private String name;
    private String mobileNumber;
    private String email;

    public LoginResponse() {
    }

    public LoginResponse(String token, String ownerType, Long ownerId, String name, String mobileNumber, String email) {
        this.token = token;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }
}

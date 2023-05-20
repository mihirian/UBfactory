package com.example.ubfactory.objects;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String ownerType;
    private Long ownerId;
    private String message;
    private String resposneCode;
    private String reponseMsg;

    public LoginResponse() {
    }

    public LoginResponse(String token, String ownerType, Long ownerId, String message, String resposneCode, String reponseMsg) {
        this.token = token;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.message = message;
        this.resposneCode = resposneCode;
        this.reponseMsg = reponseMsg;
    }
}

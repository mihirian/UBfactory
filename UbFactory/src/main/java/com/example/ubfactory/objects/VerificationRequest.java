package com.example.ubfactory.objects;

import lombok.Data;

@Data
public class VerificationRequest
{
    private String email;
    private String otp;
}

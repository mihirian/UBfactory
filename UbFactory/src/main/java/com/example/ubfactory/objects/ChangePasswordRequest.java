package com.example.ubfactory.objects;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    int id;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}

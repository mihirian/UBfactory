package com.example.ubfactory.objects;

import lombok.Data;

import javax.persistence.Id;

@Data
public class TokenResponse
{
    @Id
    private int id;
    private int ownerId;

    private String message;
}

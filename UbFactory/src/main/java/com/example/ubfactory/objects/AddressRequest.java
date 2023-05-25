package com.example.ubfactory.objects;

import lombok.Data;

@Data
public class AddressRequest
{
    private String email;
    private String streetAddress;
    private String town;
    private String state;
    private String pinCode;
}

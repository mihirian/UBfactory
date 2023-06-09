package com.example.ubfactory.objects;

import lombok.Data;

@Data
public class AddressObject {
    private String email;
    private String streetAddress;
    private String town;
    private String state;
    private String pinCode;
    private String lat;
    private String lon;

}

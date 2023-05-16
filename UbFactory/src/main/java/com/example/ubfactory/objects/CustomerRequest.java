package com.example.ubfactory.objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CustomerRequest {
    protected Integer id;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String password;
    private String ownertype;
}

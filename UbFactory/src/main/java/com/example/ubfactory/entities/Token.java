package com.example.ubfactory.entities;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Token extends BaseEntity {

    private Integer ownerId;
    private String token;
    private String status;


}

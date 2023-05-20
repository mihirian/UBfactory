package com.example.ubfactory.entities;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Token extends BaseEntity
{

     private Integer ownerId;
     private String token;
     private String status;



}

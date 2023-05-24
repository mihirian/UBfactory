package com.example.ubfactory.objects;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@Data
public class ShippingObject {
    public static final Logger logger = LoggerFactory.getLogger(ShippingObject.class);
    private String charges;
    private Date createdAt;
    private Date updatedAt;
}

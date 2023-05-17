package com.example.ubfactory.objects;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Data
public class OrderRequestObject {
    public static final Logger logger = LoggerFactory.getLogger(OrderRequestObject.class);
    private Integer customerId;
    private Integer productId;
    private BigDecimal amount;

}

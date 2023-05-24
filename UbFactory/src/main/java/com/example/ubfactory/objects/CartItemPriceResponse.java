package com.example.ubfactory.objects;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
public class CartItemPriceResponse {
    public static final Logger logger = LoggerFactory.getLogger(CartItemPriceResponse.class);
    private BigDecimal totalPrice;
    private BigDecimal price;
    private BigDecimal discount;
    private String shippingCharges;
    private BigDecimal OrderAmount;
}

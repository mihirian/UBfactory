package com.example.ubfactory.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Integer itemId;
    private String productName;
    private BigDecimal price;
    private int quantity;

    // Getters and setters
}

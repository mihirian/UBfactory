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
    private String description;
    private Integer productId;
    private String image;
    private String name;
    private BigDecimal originalPrice;
    // Getters and setters
}

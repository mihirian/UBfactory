package com.example.ubfactory.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCartItemRequest {
    private Integer productId;
    private int quantity;

    // Getters and setters
}

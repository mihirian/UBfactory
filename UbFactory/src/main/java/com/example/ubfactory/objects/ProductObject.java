package com.example.ubfactory.objects;

import com.example.ubfactory.entities.Category;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class ProductObject {

    private String name;

    private String description;

    private String categoryName;

    private String imageUrl;

    private BigDecimal price;

    private Integer stockQuantity;

    private BigDecimal originalPrice;

    private Category category;

}

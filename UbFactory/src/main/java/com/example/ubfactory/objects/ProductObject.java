package com.example.ubfactory.objects;

import com.example.ubfactory.entities.Category;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ToString
public class ProductObject {

    @NotBlank(message = "Name can not blank or null")
    private String name;

    private String description;

    private String categoryName;

    private String imageUrl;

    private BigDecimal price;

    private Integer stockQuantity;

    private BigDecimal originalPrice;

    private Category category;

}

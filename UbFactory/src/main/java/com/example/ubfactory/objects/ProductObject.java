package com.example.ubfactory.objects;

import com.example.ubfactory.entities.Category;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class ProductObject {

    private Integer productId;

    @NotBlank(message = "Name can not blank or null")
    private String name;

    private String displayName;

    private String description;

    private String status;

    private String categoryName;

    private String imageUrl;

    private BigDecimal price;

    private Integer stockQuantity;

    private BigDecimal originalPrice;

    private Date createdDate;

    private Category category;

}

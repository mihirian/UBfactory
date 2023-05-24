package com.example.ubfactory.validator;

import com.example.ubfactory.entities.Category;
import com.example.ubfactory.exception.ProductException;
import com.example.ubfactory.objects.ProductObject;
import com.example.ubfactory.utils.ResponseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Component
public class ProductValidator {
    Logger LOGGER = LoggerFactory.getLogger(ProductValidator.class);

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


    public void validateProduct(ProductObject productObject) {
        if (productObject.getName().isBlank())
            throw new ProductException(ResponseConstants.INVALID_PRODUCT_NAME);

        if (productObject.getDisplayName().isBlank()) {
            throw new ProductException(ResponseConstants.INVALID_PRODUCT_DISPLAY_NAME);
        }

        if (productObject.getImageUrl().isBlank()) {
            throw new ProductException(ResponseConstants.INVALID_IMAGE_URL);
        }
    }
}

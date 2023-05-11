package com.example.ubfactory.helper;

import com.example.ubfactory.entities.Category;
import com.example.ubfactory.entities.Product;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.objects.ProductObject;
import com.example.ubfactory.repository.CategoryRepository;
import com.example.ubfactory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.concurrent.Callable;

@Component
public class ProductHelper {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public Category getCategoryEntity(ProductObject productObject) {
        if (ObjectUtils.isEmpty(productObject.getCategory())) {

        }
        Category category = categoryRepository.findByName(productObject.getCategory().getName());
        if (!ObjectUtils.isEmpty(category)) {
            return category;
        } else {
            category = productObject.getCategory();
            category.setStatus(Status.ACTIVE.getStatus());
            category.setCreatedAt(new Date());
            category.setUpdatedAt(new Date());
            category = categoryRepository.save(category);
        }
        return category;
    }

    public Product getProductEntity(ProductObject productObject, Category category) {
        Product product = new Product();
        product.setName(productObject.getName());
        product.setDescription(productObject.getDescription());
        product.setPrice(productObject.getPrice());
        product.setOriginalPrice(productObject.getOriginalPrice());
        product.setCategory(category);
        product.setStatus(Status.ACTIVE.getStatus());
        product.setImageUrl(productObject.getImageUrl());
        product.setStockQuantity(productObject.getStockQuantity());
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        productRepository.save(product);
        return product;
    }
}

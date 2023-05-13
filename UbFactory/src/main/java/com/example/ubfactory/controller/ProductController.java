package com.example.ubfactory.controller;

import com.example.ubfactory.entities.Product;
import com.example.ubfactory.objects.ProductObject;
import com.example.ubfactory.service.ProductService;
import com.example.ubfactory.service.serviceimpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductServiceImpl productService;
    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductObject> createProduct(@Valid @RequestBody ProductObject product){
        ProductObject createdProduct = productService.createProduct(product);
        return new  ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

}

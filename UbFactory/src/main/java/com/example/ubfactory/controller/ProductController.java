package com.example.ubfactory.controller;

import com.example.ubfactory.entities.Product;
import com.example.ubfactory.objects.ProductObject;
import com.example.ubfactory.service.ProductService;
import com.example.ubfactory.service.serviceimpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductObject> createProduct(@Valid @RequestBody ProductObject product) {
        ProductObject createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<ProductObject> updateProduct(@Valid @RequestBody ProductObject product) {
        ProductObject createdProduct = productService.updateProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductObject>> getAllProducts() {
        List<ProductObject> productObjectList = productService.getAllProducts();
        return new ResponseEntity<>(productObjectList, HttpStatus.OK);
    }

    @GetMapping(path = "/get/all/{category}")
    public ResponseEntity<List<ProductObject>> getAllProductsByCategory(@PathVariable String category) {
        List<ProductObject> productObjectList = productService.getAllProductsByCategoryName(category);
        return new ResponseEntity<>(productObjectList, HttpStatus.OK);
    }

    @DeleteMapping(path = "delete/{productId}")
    public ResponseEntity<ProductObject> deleteProductById(@PathVariable Integer productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}

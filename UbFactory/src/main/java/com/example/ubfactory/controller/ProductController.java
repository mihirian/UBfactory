package com.example.ubfactory.controller;

import com.example.ubfactory.objects.GenericResponse;
import com.example.ubfactory.objects.ProductObject;
import com.example.ubfactory.service.ProductService;
import com.example.ubfactory.utils.Request;
import com.example.ubfactory.utils.Response;
import com.example.ubfactory.utils.ResponseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Response<ProductObject> createProduct(@Valid @RequestBody Request<ProductObject> product) {
        GenericResponse<ProductObject> response = new GenericResponse<>();
        ProductObject createdProduct = productService.createProduct(product.getData());
        return response.createSuccessResponse(createdProduct, HttpStatus.CREATED.value(), ResponseConstants.PRODUCT_REGISTERED);
    }

    @PostMapping("/update")
    public Response<ProductObject> updateProduct(@Valid @RequestBody ProductObject product) {
        GenericResponse<ProductObject> response = new GenericResponse<>();
        ProductObject createdProduct = productService.updateProduct(product);
        return response.createSuccessResponse(createdProduct, HttpStatus.MULTI_STATUS.value(), ResponseConstants.PRODUCT_UPDATED);
    }

    @GetMapping
    public Response<ProductObject> getAllProducts() {
        GenericResponse<ProductObject> response = new GenericResponse<>();
        List<ProductObject> productObjectList = productService.getAllProducts();
        return response.createSuccessListResponse(productObjectList);
    }

    @GetMapping(path = "/get/all/{category}")
    public Response<ProductObject> getAllProductsByCategory(@PathVariable String category) {
        GenericResponse<ProductObject> response = new GenericResponse<>();
        List<ProductObject> productObjectList = productService.getAllProductsByCategoryName(category);
        return response.createSuccessListResponse(productObjectList);
    }

    @DeleteMapping(path = "delete/{productId}")
    public Response<ProductObject> deleteProductById(@PathVariable Integer productId) {
        GenericResponse<ProductObject> response = new GenericResponse<>();
        productService.deleteProductById(productId);
        return response.createSuccessResponse(null, HttpStatus.OK.value(), ResponseConstants.PRODUCT_DELETED);
    }
}

package com.example.ubfactory.service;

import com.example.ubfactory.objects.ProductObject;

import java.util.List;


public interface ProductService {

    public ProductObject createProduct(ProductObject product);

    public ProductObject getProductByName(String name);

    public List<ProductObject> getAllProducts();

    public List<ProductObject> getAllProductsByCategoryName(String name);


    public void deleteProductById(Integer productId);

    ProductObject updateProduct(ProductObject product);

}

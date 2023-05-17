package com.example.ubfactory.service;

import com.example.ubfactory.exception.ProductException;
import com.example.ubfactory.objects.ProductObject;

import java.util.List;


public interface ProductService {

    public ProductObject createProduct(ProductObject product) throws ProductException;

    public ProductObject getProductByName(String name) throws ProductException;

    public List<ProductObject> getAllProducts() throws  ProductException;

    public List<ProductObject> getAllProductsByCategoryName(String name) throws ProductException;


    public void deleteProductById(Integer productId) throws ProductException;

    ProductObject updateProduct(ProductObject product) throws ProductException;

}

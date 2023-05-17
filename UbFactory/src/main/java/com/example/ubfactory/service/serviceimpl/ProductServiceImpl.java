package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Category;
import com.example.ubfactory.entities.Product;
import com.example.ubfactory.helper.ProductHelper;
import com.example.ubfactory.objects.ProductObject;
import com.example.ubfactory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductHelper productHelper;

    @Override
    public ProductObject createProduct(ProductObject productObject) {

        Category category = productHelper.getCategoryEntity(productObject);
        Product product = productHelper.getProductEntity(productObject, category);
        ProductObject objectFromEntity = productHelper.getObjectFromEntity(product);
        objectFromEntity.setCategoryName(category.getName());

        return objectFromEntity;
    }

    @Override
    public List<ProductObject> getAllProducts() {
        List<ProductObject> productObjects = productHelper.getAllProducts();
        return productObjects;
    }

    @Override
    public List<ProductObject> getAllProductsByCategoryName(String name) {
        List<ProductObject> allProductsByCategoryName = productHelper.getAllProductsByCategoryName(name);
        return allProductsByCategoryName;
    }


    @Override
    public ProductObject getProductByName(String name) {
        ProductObject product = productHelper.getSingleProductName(name);
        return product;
    }

    @Override
    public void deleteProductById(Integer productId) {
        productHelper.deleteProductId(productId);
    }

    @Override
    public ProductObject updateProduct(ProductObject product) {
        ProductObject productObject = productHelper.updateProduct(product);
        return productObject;
    }
}

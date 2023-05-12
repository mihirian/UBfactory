package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Category;
import com.example.ubfactory.entities.Product;
import com.example.ubfactory.helper.ProductHelper;
import com.example.ubfactory.objects.ProductObject;
import com.example.ubfactory.repository.CategoryRepository;
import com.example.ubfactory.repository.ProductRepository;
import com.example.ubfactory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl {

    @Autowired
    private ProductHelper productHelper;

    public ProductObject createProduct(ProductObject productObject) {


        Category category = productHelper.getCategoryEntity(productObject);

        Product product = productHelper.getProductEntity(productObject,category);

        productObject.setCategoryName(category.getName());

        return productObject;
    }

    public Product productFindById(Integer id) {
        return null;
    }

    public List<Product> findAllProducts() {
        return null;
    }
}

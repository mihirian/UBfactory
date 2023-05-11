package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Category;
import com.example.ubfactory.entities.Product;
import com.example.ubfactory.repository.ProductRepository;
import com.example.ubfactory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product saveProduct(Product product) {
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        Category cat =new Category();
        cat.setId(1);
        product.setCategory(cat);
        return productRepository.save(product);
    }

    @Override
    public Product productFindById(Integer id) {
        return null;
    }

    @Override
    public List<Product> findAllProducts() {
        return null;
    }
}

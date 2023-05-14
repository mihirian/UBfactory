package com.example.ubfactory.helper;

import com.example.ubfactory.entities.Category;
import com.example.ubfactory.entities.Product;
import com.example.ubfactory.enums.Status;
import com.example.ubfactory.exception.ProductException;
import com.example.ubfactory.objects.ProductObject;
import com.example.ubfactory.repository.CategoryRepository;
import com.example.ubfactory.repository.ProductRepository;
import com.example.ubfactory.utils.ResponseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ProductHelper {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public ProductObject getObjectFromEntity(Product product) {
        ProductObject productObject = new ProductObject();
        productObject.setProductId(product.getId());
        productObject.setName(product.getName());
        productObject.setDisplayName(product.getDisplayName());
        productObject.setCategoryName(product.getCategory().getName());
        productObject.setDescription(product.getDescription());
        productObject.setStatus(product.getStatus());
        productObject.setImageUrl(product.getImageUrl());
        productObject.setPrice(product.getPrice());
        productObject.setOriginalPrice(product.getOriginalPrice());
        productObject.setStockQuantity(product.getStockQuantity());
        productObject.setCreatedDate(product.getCreatedAt());
        return productObject;
    }

    public Category getCategoryEntity(ProductObject productObject) {
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
        product.setDisplayName(productObject.getDisplayName());
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

    public List<ProductObject> getAllProducts() {
        List<ProductObject> productObjectList = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        if (CollectionUtils.isEmpty(products)) {
            throw new ProductException(ResponseConstants.NO_ANY_PRODUCT_FOUND);
        }
        products.stream().forEach(product -> {
            ProductObject productObject = new ProductObject();
            productObject.setProductId(product.getId());
            productObject.setName(product.getName());
            productObject.setDisplayName(product.getDisplayName());
            productObject.setCategoryName(product.getCategory().getName());
            productObject.setDescription(product.getDescription());
            productObject.setImageUrl(product.getImageUrl());
            productObject.setPrice(product.getPrice());
            productObject.setOriginalPrice(product.getOriginalPrice());
            productObject.setStockQuantity(product.getStockQuantity());
            productObject.setCreatedDate(product.getCreatedAt());
            productObjectList.add(productObject);
        });
        return productObjectList;
    }

    public List<ProductObject> getAllProductsByCategoryName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        if (ObjectUtils.isEmpty(category)) throw new ProductException(ResponseConstants.FOOD_CATEGORY_NOT_FOUND);
        List<ProductObject> productObjectList = new ArrayList<>();
        List<Product> products = productRepository.findAllByCategoryId(category.getId());
        if (CollectionUtils.isEmpty(products)) {
            throw new ProductException(ResponseConstants.NO_ANY_PRODUCT_FOUND);
        }
        products.stream().forEach(product -> {
            ProductObject productObject = new ProductObject();
            productObject.setProductId(product.getId());
            productObject.setName(product.getName());
            productObject.setDisplayName(product.getDisplayName());
            productObject.setCategoryName(product.getCategory().getName());
            productObject.setDescription(product.getDescription());
            productObject.setImageUrl(product.getImageUrl());
            productObject.setPrice(product.getPrice());
            productObject.setOriginalPrice(product.getOriginalPrice());
            productObject.setStockQuantity(product.getStockQuantity());
            productObject.setCreatedDate(product.getCreatedAt());
            productObjectList.add(productObject);
        });
        return productObjectList;
    }

    public ProductObject getSingleProductName(String name) {
        Product product = productRepository.findByName(name);
        if (ObjectUtils.isEmpty(product))
            throw new ProductException(ResponseConstants.PRODUCT_NOT_FOUND);
        ProductObject productObject = new ProductObject();
        productObject.setProductId(product.getId());
        productObject.setName(product.getName());
        productObject.setDisplayName(product.getDisplayName());
        productObject.setCategoryName(product.getCategory().getName());
        productObject.setDescription(product.getDescription());
        productObject.setImageUrl(product.getImageUrl());
        productObject.setPrice(product.getPrice());
        productObject.setOriginalPrice(product.getOriginalPrice());
        productObject.setStockQuantity(product.getStockQuantity());
        productObject.setCreatedDate(product.getCreatedAt());
        return productObject;
    }

    public void deleteProductId(Integer productId) {
        if (productRepository.existsById(productId)) {
            Optional<Product> byId = productRepository.findById(productId);
            Product product = byId.get();
            product.setStatus(Status.INACTIVE.getStatus());
            productRepository.save(product);
        }
    }

    public ProductObject updateProduct(ProductObject productObject) {
        if (!productRepository.existsById(productObject.getProductId()))
            throw new ProductException(ResponseConstants.PRODUCT_NOT_FOUND);
        Product product = productRepository.findById(productObject.getProductId()).get();
        if (productObject.getName() != null && !productObject.getName().isBlank())
            product.setName(productObject.getName());
        if (productObject.getDisplayName() != null && !productObject.getDisplayName().isBlank())
            product.setDisplayName(productObject.getDisplayName());
        if (productObject.getDescription() != null && !productObject.getDescription().isBlank())
            product.setDescription(productObject.getDescription());
        if (productObject.getStatus() != null && !productObject.getStatus().isBlank())
            product.setStatus(productObject.getStatus());
        if (productObject.getPrice() != null)
            product.setPrice(productObject.getPrice());
        if (productObject.getOriginalPrice() != null)
            product.setOriginalPrice(productObject.getOriginalPrice());
        if (productObject.getImageUrl() != null && !productObject.getImageUrl().isBlank())
            product.setImageUrl(productObject.getImageUrl());
        if (productObject.getStockQuantity() != null)
            product.setStockQuantity(productObject.getStockQuantity());
        product.setUpdatedAt(new Date());
        product = productRepository.save(product);
        ProductObject objectFromEntity = getObjectFromEntity(product);
        return objectFromEntity;
    }
}


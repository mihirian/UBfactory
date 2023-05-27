package com.example.ubfactory.service.serviceimpl;

import com.example.ubfactory.entities.Customer;
import com.example.ubfactory.entities.Product;
import com.example.ubfactory.entities.Rating;
import com.example.ubfactory.helper.RatingHelper;
import com.example.ubfactory.objects.RatingRequestObject;
import com.example.ubfactory.repository.CustomerRepository;
import com.example.ubfactory.repository.ProductRepository;
import com.example.ubfactory.repository.RatingRepository;
import com.example.ubfactory.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    public static final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private RatingHelper ratingHelper;

    @Override
    public String createRating(RatingRequestObject ratingRequestObject) {
        Customer customer = customerRepository.findById(ratingRequestObject.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Product product = productRepository.findById(ratingRequestObject.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Rating rating = new Rating();
        rating.setCustomer(customer);
        rating.setPoint(ratingRequestObject.getPoint());
        rating.setRemark(ratingRequestObject.getRemark());
        rating.setCreatedAt(new Date());
        rating.setUpdatedAt(new Date());
        rating.setProduct(product);
        ratingRepository.save(rating);
        return "Success";
    }

    @Override
    public RatingRequestObject getByProductId(Integer productId) {
        List<Rating> rating = ratingRepository.findByProductId(productId);
        Double avgRating = ratingHelper.calculateAverageRating(productId);
        RatingRequestObject ratingRequestObject1 = new RatingRequestObject();
        List<RatingRequestObject> objectList =new ArrayList<>();
        if(rating != null) {
            for (Rating rating1 : rating) {
                RatingRequestObject ratingRequestObject = new RatingRequestObject();
                ratingRequestObject.setCustomerName(rating1.getCustomer().getFirstName());
                ratingRequestObject.setCustomerId(rating1.getCustomer().getId());
                ratingRequestObject.setRemark(rating1.getRemark());
                ratingRequestObject.setPoint(rating1.getPoint());
                ratingRequestObject.setCreatedAt(rating1.getCreatedAt());
                objectList.add(ratingRequestObject);
            }
        }
        ratingRequestObject1.setProductRatingList(objectList);
        ratingRequestObject1.setProductAverageRating(avgRating.toString());
        return ratingRequestObject1;
    }
}

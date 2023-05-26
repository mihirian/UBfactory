package com.example.ubfactory.repository;

import com.example.ubfactory.entities.Banner;
import com.example.ubfactory.entities.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderSummaryRepository  extends JpaRepository<OrderSummary, Integer> {
    OrderSummary findByRazorpayId(String razorpayId);


    List<OrderSummary> findAllByCustomerId(Integer customerId);
}

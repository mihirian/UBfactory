package com.example.ubfactory.repository;

import com.example.ubfactory.entities.OrderItem;
import com.example.ubfactory.entities.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem ,Integer> {

    List<OrderItem> findAllByOrderSummary(OrderSummary orderSummary);
}

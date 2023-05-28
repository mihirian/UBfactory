package com.example.ubfactory.repository;

import com.example.ubfactory.entities.PaymentSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentSummary,Integer> {
    PaymentSummary findByOrderId(Integer id);
}

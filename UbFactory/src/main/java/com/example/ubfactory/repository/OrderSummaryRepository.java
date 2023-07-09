package com.example.ubfactory.repository;

import com.example.ubfactory.entities.OrderSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderSummaryRepository extends JpaRepository<OrderSummary, Integer> {
  

    OrderSummary findByRazorpayId(String razorpayId);


    List<OrderSummary> findAllByCustomerId(Integer customerId);

    Page<OrderSummary> findByCreatedAtBetween(Date startDateTime, Date endDateTime, Pageable pageable);



}
    



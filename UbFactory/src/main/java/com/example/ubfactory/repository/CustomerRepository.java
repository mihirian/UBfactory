package com.example.ubfactory.repository;

import com.example.ubfactory.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // Additional methods if needed
    Customer findByemail(String email);
}

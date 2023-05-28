package com.example.ubfactory.repository;

import com.example.ubfactory.entities.CustomerCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCopyRepo extends JpaRepository<CustomerCopy, Integer> {
    CustomerCopy findByemail(String email);
}

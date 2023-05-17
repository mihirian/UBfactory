package com.example.ubfactory.repository;

import com.example.ubfactory.entities.Shipping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping, Integer> {
    public static final Logger logger = LoggerFactory.getLogger(ShippingRepository.class);
}

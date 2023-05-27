package com.example.ubfactory.repository;

import com.example.ubfactory.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating ,Integer> {
    List<Rating> findByProductId(Integer productId);
}

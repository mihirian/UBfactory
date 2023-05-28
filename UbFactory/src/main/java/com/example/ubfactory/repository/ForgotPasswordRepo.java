package com.example.ubfactory.repository;

import com.example.ubfactory.entities.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword,Integer> {
    ForgotPassword findByemail(String email);
}

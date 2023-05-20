package com.example.ubfactory.repository;

import com.example.ubfactory.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TokenDao extends JpaRepository<Token,Integer> {


    Token findByownerId(Integer ownerId);

    void deleteByownerId(Integer ownerId);
}

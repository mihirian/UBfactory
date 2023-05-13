package com.example.ubfactory.repository;

import com.example.ubfactory.entities.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarqueRepository extends JpaRepository<Marque,Integer> {

    Marque findByMarqueeName(String marqueeName);
}

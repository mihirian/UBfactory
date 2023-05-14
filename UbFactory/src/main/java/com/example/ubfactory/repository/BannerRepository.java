package com.example.ubfactory.repository;


import com.example.ubfactory.entities.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Integer> {
    @Query("select b from Banner b where b.startDate <= :date and b.endDate >= :date")
    List<Banner> findBannerListBystartDate(@Param("date") Date date);

}

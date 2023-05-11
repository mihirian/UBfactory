package com.example.ubfactory.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "banners")
public class Banner extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "status", nullable = false)
    private String status;

    // Getters and Setters
}



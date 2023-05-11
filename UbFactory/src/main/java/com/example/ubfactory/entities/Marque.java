package com.example.ubfactory.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "marque")
public class Marque extends BaseEntity {

    @Column(name = "marque_text", nullable = false)
    private String marqueText;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "status", nullable = false)
    private String status;

    // Getters and Setters
}
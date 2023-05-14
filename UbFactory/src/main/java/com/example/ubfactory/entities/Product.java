package com.example.ubfactory.entities;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString

@Entity
@Table(name = "product")
public class Product extends BaseEntity {

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "dispaly_name", nullable = false)
    private String displayName;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "original_price", nullable = false)
    private BigDecimal originalPrice;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    private Set<Feedback> feedbacks;

    // getters and setters
}


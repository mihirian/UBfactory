package com.example.ubfactory.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    // getters and setters
}

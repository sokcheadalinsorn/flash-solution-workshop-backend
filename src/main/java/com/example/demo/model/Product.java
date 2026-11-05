package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship with Brand
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    // Relationship with Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    String name;

    BigDecimal price;

    String description;

    @Column(name = "stock_quantity")
    Integer stockQuantity;

    @Column(name = "image_url")
    String imageUrl;

    String sku;

    public Product() {
    }
}
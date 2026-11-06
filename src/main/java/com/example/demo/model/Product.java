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

    public Long getId() {
    return id;
}

public Brand getBrand() {
    return brand;
}

public Category getCategory() {
    return category;
}

public String getName() {
    return name;
}

public BigDecimal getPrice() {
    return price;
}

public String getDescription() {
    return description;
}

public Integer getStockQuantity() {
    return stockQuantity;
}

public String getImageUrl() {
    return imageUrl;
}

public String getSku() {
    return sku;
}
}

// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.math.BigDecimal;

// @Entity
// @Table(name = "products")
// public class Product {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String brand;

//     private String name;

//     private BigDecimal price;

//     private String description;

//     public Product() {
//     }
// }     
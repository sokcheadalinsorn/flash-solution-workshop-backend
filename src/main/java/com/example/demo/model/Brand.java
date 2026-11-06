package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String logo;

    public Long getId() {
    return id;
}

public String getName() {
    return name;
}

public String getLogo() {
    return logo;
}
}

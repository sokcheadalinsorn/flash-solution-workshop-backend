package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    public Long getId() {
    return id;
}

public String getName() {
    return name;
}
}
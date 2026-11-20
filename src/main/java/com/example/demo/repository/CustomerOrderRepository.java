package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CustomerOrder;

public interface CustomerOrderRepository
        extends JpaRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByCustomer_IdOrderByCreatedAtDesc(
        Long userId
    );
}
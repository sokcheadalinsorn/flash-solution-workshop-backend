package com.example.demo.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_orders")
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User customer;

    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String paymentMethod;

    private String status = "PENDING";

    @Column(precision = 12, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    private Instant createdAt;

    @ElementCollection
    @CollectionTable(
        name = "customer_order_items",
        joinColumns = @JoinColumn(name = "order_id")
    )
    private List<OrderLine> items = new ArrayList<>();

    public CustomerOrder() {}

    @PrePersist
    public void onCreate() {
        createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public CustomerOrder(
            User customer,
            String fullName,
            String email,
            String phone,
            String address,
            String city,
            String paymentMethod) {

        this.customer = customer;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.paymentMethod = paymentMethod;
    }

    public void addItem(
            Long productId,
            String productName,
            BigDecimal price,
            int quantity) {

        items.add(
            new OrderLine(productId, productName, price, quantity)
        );

        totalAmount = totalAmount.add(
            price.multiply(BigDecimal.valueOf(quantity))
        );
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Embeddable
    public static class OrderLine {

        private Long productId;
        private String productName;

        @Column(precision = 12, scale = 2)
        private BigDecimal unitPrice;

        private Integer quantity;

        public OrderLine() {}

        public OrderLine(
                Long productId,
                String productName,
                BigDecimal unitPrice,
                Integer quantity) {

            this.productId = productId;
            this.productName = productName;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
        }
    }
}
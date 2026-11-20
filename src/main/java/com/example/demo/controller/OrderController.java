package com.example.demo.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.model.CustomerOrder;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.CustomerOrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(
    origins = "http://localhost:5173",
    allowCredentials = "true"
)
public class OrderController {

    private final CustomerOrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderController(
            CustomerOrderRepository orderRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null ||
                !(session.getAttribute("userId") instanceof Long userId)) {

            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Please log in first"
            );
        }

        return userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "User not found"
            ));
    }

    // POST /api/orders
    @PostMapping
    @Transactional
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request,
            HttpServletRequest httpRequest) {

        User user = getCurrentUser(httpRequest);

        CustomerOrder order = new CustomerOrder(
            user,
            request.fullName(),
            request.email(),
            request.phone(),
            request.address(),
            request.city(),
            request.paymentMethod()
        );

        for (CreateOrderRequest.Item item : request.items()) {

            Product product = productRepository
                .findById(item.productId())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Product not found: " + item.productId()
                ));

            BigDecimal price = BigDecimal
                .valueOf(product.getPrice())
                .setScale(2, RoundingMode.HALF_UP);

            order.addItem(
                product.getId(),
                product.getName(),
                price,
                item.quantity()
            );
        }

        CustomerOrder saved = orderRepository.save(order);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(toResponse(saved));
    }

    // GET /api/orders/my
    @GetMapping("/my")
    public List<OrderResponse> getMyOrders(
            HttpServletRequest request) {

        User user = getCurrentUser(request);

        return orderRepository
            .findByCustomer_IdOrderByCreatedAtDesc(user.getId())
            .stream()
            .map(this::toResponse)
            .toList();
    }

    private OrderResponse toResponse(CustomerOrder order) {

        return new OrderResponse(
            order.getId(),
            order.getStatus(),
            order.getTotalAmount(),
            order.getCreatedAt()
        );
    }

    public record OrderResponse(
        Long id,
        String status,
        BigDecimal totalAmount,
        Instant createdAt
    ) {}
}
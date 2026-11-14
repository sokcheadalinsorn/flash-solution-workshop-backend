package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // Constructor
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Method to add a new product (Method create)
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Method to retrieve all products (method read )
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Method to retrieve a product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    
    }

    // Method to update a product
    public Product updateProduct(Long id, Product updatedProduct) {

        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found"
            ));

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStock_quanity(updatedProduct.getStock_quanity());
        product.setImageUrl(updatedProduct.getImageUrl());
        product.setSku(updatedProduct.getSku());

        return productRepository.save(product);
    }

    
    // Method to delete a product
    public void deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Product not found"
            );
        }

        productRepository.deleteById(id);
    }


}
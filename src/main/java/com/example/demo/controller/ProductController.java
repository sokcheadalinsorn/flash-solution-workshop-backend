package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // Constructor
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // POST: Create a product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    // GET: Retrieve a product by ID
   @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // PUT: Update a product
    @PutMapping("/{id}")
    public Product updateProduct(
        @PathVariable Long id,
        @RequestBody Product product) {

        return productService.updateProduct(id, product);
    } 

    // DELETE: Delete a product
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {

    productService.deleteProduct(id);
    }

    public ProductService getProductService() {
        return productService;
    }
}
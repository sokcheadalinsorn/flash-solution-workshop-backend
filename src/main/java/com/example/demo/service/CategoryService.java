package com.example.demo.service;

import java.util.List;
import java.util.Locale.Category;

import org.springframework.stereotype.Service;

/**
 * CategoryService
 */
@Service 
public interface CategoryService {

    Category creaCategory(com.example.demo.model.Category category);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category UpdateCategory(Long id, Category category);
    void deleteCategory(Long id);


}

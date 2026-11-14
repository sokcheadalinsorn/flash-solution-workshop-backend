package com.example.demo.service;

import java.util.Locale;
import java.util.Locale.Category;

import org.springframework.stereotype.Service;

import com.example.demo.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service 
@Transactional (readOnly = true)
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override 
    @Transactional 
    public Category creaCategory(Category category) {
        category.setId(null);
        category.setName(text(category.getName(), "category name"));
        category.setSlug(text(category.getSlug(), "category slug"). toLowerCase(Locale.ROOT));
        category.setDes(text(category.getDes(), "category des"));

        checkUnique(category.getName(), category.getSlug(), null, null);
        return category.save(category);

    }
    
}

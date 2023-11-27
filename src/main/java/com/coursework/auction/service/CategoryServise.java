package com.coursework.auction.service;

import com.coursework.auction.entity.Category;
import com.coursework.auction.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServise {

    @Autowired
    CategoryRepository categoryRepository;

    public Category getCategory(String category) {
        return categoryRepository.findByCategory(category);
    }

    public List<Category> getAllCategories() { return categoryRepository.findAll();}
}

package com.coursework.auction.controller;

import com.coursework.auction.entity.Category;
import com.coursework.auction.service.CategoryServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryServise categoryServise;

    @GetMapping
    public List<Category> getAllCategoryes() {
        return categoryServise.getAllCategories();
    }
}

package com.coursework.auction.repository;

import com.coursework.auction.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategory(String category);
}

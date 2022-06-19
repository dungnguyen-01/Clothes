package com.clothes.repository.service;

import com.clothes.repository.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    void create(Category item);

    Category edit(Integer id);

    void update(Category item);

    void delete(Integer id);

    Integer checkCategoryName(String name);
}

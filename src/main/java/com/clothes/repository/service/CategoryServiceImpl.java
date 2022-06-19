package com.clothes.repository.service;

import com.clothes.repository.Category;
import com.clothes.repository.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryDAO dao;

    @Override
    public List<Category> findAll() {
        return dao.findAll();
    }

    @Override
    public void create(Category item) {
        dao.save(item);
    }

    @Override
    public Category edit(Integer id) {
        return dao.getById(id);
    }

    @Override
    public void update(Category item) {
         dao.save(item);
    }

    @Override
    public void delete(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public Integer checkCategoryName(String name) {
        return dao.checkCategoryName(name);
    }
}

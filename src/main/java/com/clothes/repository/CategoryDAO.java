package com.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryDAO extends JpaRepository<Category,Integer> {
    @Query("SELECT count(c) FROM  Category c WHERE  c.cname=?1")
    Integer checkCategoryName(String name);
}

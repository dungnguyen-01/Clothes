package com.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductColorDAO extends JpaRepository<ProductColor,Integer> {
    @Query("SELECT o FROM ProductColor o WHERE o.product.pid=?1")
    List<ProductColor> findByProduct(Integer id);
}

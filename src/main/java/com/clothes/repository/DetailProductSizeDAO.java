package com.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface DetailProductSizeDAO extends JpaRepository<DetailProductSize,Integer> {
    @Query("SELECT o FROM DetailProductSize o WHERE o.product.pid = ?1")
    List<DetailProductSize> findByProduct(Integer id);

}

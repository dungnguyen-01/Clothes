package com.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ImageDAO extends JpaRepository<Image,Integer> {
    @Query("SELECT o FROM Image o WHERE o.product.pid = ?1")
    List<Image> findByProduct(Integer id);

}

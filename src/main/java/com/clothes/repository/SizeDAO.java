package com.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SizeDAO extends JpaRepository<Size,Integer> {
    @Query("SELECT COUNT (s) FROM  Size s WHERE s.sname=?1")
    Integer checkSizeName(String name);

    List<Size> findAllByTypeOfItemTrue();

    List<Size> findAllByTypeOfItemFalse();
}

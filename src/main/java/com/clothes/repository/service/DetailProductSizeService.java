package com.clothes.repository.service;

import com.clothes.repository.DetailProductSize;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetailProductSizeService {

    @Query("SELECT o FROM DetailProductSize o WHERE o.product.pid=?1")
    List<DetailProductSize> findByProductId(Integer id);

    void deleteAllBy(List<Integer> listDetailP);
}

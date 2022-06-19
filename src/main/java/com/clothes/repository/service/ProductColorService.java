package com.clothes.repository.service;

import com.clothes.repository.ProductColor;

import java.util.List;

public interface ProductColorService {
    List<ProductColor> findByProductId(Integer id);
}

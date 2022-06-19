package com.clothes.repository.service;

import com.clothes.repository.ProductColor;
import com.clothes.repository.ProductColorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductColorServiceImpl implements ProductColorService{

    @Autowired
    ProductColorDAO dao;
    @Override
    public List<ProductColor> findByProductId(Integer id) {
        return dao.findByProduct(id);
    }
}

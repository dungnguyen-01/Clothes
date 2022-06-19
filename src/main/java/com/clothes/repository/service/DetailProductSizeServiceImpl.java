package com.clothes.repository.service;

import com.clothes.repository.DetailProductSize;
import com.clothes.repository.DetailProductSizeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailProductSizeServiceImpl implements DetailProductSizeService{
    @Autowired
    DetailProductSizeDAO dao;

    @Override
    public List<DetailProductSize> findByProductId(Integer id) {
        return dao.findByProduct(id);
    }

    @Override
    public void deleteAllBy(List<Integer> listDetailP) {
        dao.deleteAllById(listDetailP);
    }
}

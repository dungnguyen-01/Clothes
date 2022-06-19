package com.clothes.repository.service;

import com.clothes.repository.Size;
import com.clothes.repository.SizeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService{
    @Autowired
    SizeDAO dao;

    @Override
    public List<Size> findAll() {
        return dao.findAll();
    }

    @Override
    public void create(Size size) {
        dao.save(size);
    }

    @Override
    public void delete(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public Size edit(Integer id) {
        return dao.getById(id);
    }


    @Override
    public Integer checkSizeName(String name) {
        return dao.checkSizeName(name);
    }

    @Override
    public List<Size> findAllByShirt() {
        return dao.findAllByTypeOfItemTrue();
    }

    @Override
    public List<Size> findAllByTrousers() {
        return dao.findAllByTypeOfItemFalse();
    }
}

package com.clothes.repository.service;

import com.clothes.repository.Image;
import com.clothes.repository.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService{
    @Autowired
    ImageDAO dao;


    @Override
    public void create(List<Image> images) {
        dao.saveAll(images);
    }

    @Override
    public List<Image> findByProductId(Integer id) {
        return dao.findByProduct(id);
    }

    @Override
    public Image getById(Integer id) {
        return dao.getById(id);
    }

    @Override
    public void deleteById(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public void saveAll(List<Image> images) {
        dao.saveAll(images);
    }




}

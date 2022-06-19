package com.clothes.repository.service;

import com.clothes.repository.Image;

import java.util.List;

public interface ImageService {

    void create(List<Image> images);

    List<Image> findByProductId(Integer id);

    Image getById(Integer id);

    void deleteById(Integer id);

    void saveAll(List<Image> images);
}
package com.clothes.repository.service;

import com.clothes.repository.Size;

import java.util.List;

public interface SizeService {
    List<Size> findAll();

    void create(Size size);

    void delete(Integer id);

    Size edit(Integer id);

    Integer checkSizeName(String name);

    List<Size> findAllByShirt();

    List<Size> findAllByTrousers();
}

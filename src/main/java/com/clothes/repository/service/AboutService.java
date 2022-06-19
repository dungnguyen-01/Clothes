package com.clothes.repository.service;

import com.clothes.repository.About;

import java.util.List;

public interface AboutService {
    List<About> findAll();

    void create(About about);


    About getById(Integer id);
}

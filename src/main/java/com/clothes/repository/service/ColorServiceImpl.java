package com.clothes.repository.service;

import com.clothes.repository.Color;
import com.clothes.repository.ColorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl  implements ColorService{
    @Autowired
    ColorDAO dao;

    @Override
    public List<Color> findAll() {
        return dao.findAll();
    }
}

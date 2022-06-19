package com.clothes.repository.service;

import com.clothes.repository.About;
import com.clothes.repository.AboutDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AboutServiceImpl implements AboutService{
    @Autowired
    AboutDAO dao;

    @Override
    public List<About> findAll() {
        return dao.findAll();
    }

    @Override
    public void create(About about) {
        dao.save(about);
    }

    @Override
    public About getById(Integer id) {
        return dao.getById(id);
    }
}

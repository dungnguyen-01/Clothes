package com.clothes.repository.service;

import com.clothes.repository.Status;
import com.clothes.repository.StatusDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements  StatusService{
    @Autowired
    StatusDAO dao;

    @Override
    public List<Status> findAll() {
        return dao.findAll();
    }
}

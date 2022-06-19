package com.clothes.repository.service;

import com.clothes.repository.Role;
import com.clothes.repository.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleDAO dao;

    @Override
    public List<Role> findAll() {
        return dao.findAll();
    }
}

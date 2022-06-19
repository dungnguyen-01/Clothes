package com.clothes.repository.service;

import com.clothes.repository.Account;
import com.clothes.repository.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements  AccountService{
    @Autowired
    AccountDAO  dao;
    @Override
    public List<Account> findAll() {
        return dao.findAll();
    }

    @Override
    public void create(Account account) {
        dao.save(account);
    }

    @Override
    public Account getById(int id) {
        return  dao.getById(id);
    }

    @Override
    public void update(Account account) {
        dao.save(account);
    }

    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }


    @Override
    public boolean findByEmail(String email) {
        return dao.existsByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return dao.existsByEmail(email);
    }

    @Override
    public Account getByEmail(String email) {
        return dao.getByEmail(email);
    }
}

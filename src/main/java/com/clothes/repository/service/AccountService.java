package com.clothes.repository.service;

import com.clothes.repository.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();

    void create(Account account);

    Account getById(int id);

    void update(Account account);

    void deleteById(int id);

    boolean findByEmail(String username);

    boolean existsByEmail(String email);

    Account getByEmail(String email);
}

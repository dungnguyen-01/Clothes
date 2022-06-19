package com.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountDAO extends JpaRepository<Account,Integer> {

    //search email already exists
    boolean existsByEmail(String email);

    @Query("SELECT a FROM Account a WHERE  a.email=?1")
    Account getByEmail(String email);

}

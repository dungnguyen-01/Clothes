package com.clothes.repository.service;

import com.clothes.repository.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ContactService{
    void create(Contact contact);

    List<Contact> finAll();

    Contact getById(Integer id);

    void updateContact(Integer id, Authentication auth);


    void deleteById(Integer id);
}

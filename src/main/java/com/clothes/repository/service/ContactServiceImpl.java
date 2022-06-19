package com.clothes.repository.service;

import com.clothes.repository.Account;
import com.clothes.repository.Contact;
import com.clothes.repository.ContactDAO;
import com.clothes.security.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService{
    @Autowired
    ContactDAO dao;

    @Override
    public void create(Contact contact) {
        dao.save(contact);
    }

    @Override
    public List<Contact> finAll() {
        return dao.findAll();
    }

    @Override
    public Contact getById(Integer id) {
        return dao.getById(id);
    }

    @Transactional
    @Override
    public void updateContact(Integer id, Authentication auth) {
        UserDetailImpl userDetail = (UserDetailImpl) auth.getPrincipal();
        Account account = userDetail.getAccount();

        Contact contact = dao.getById(id);

        Contact ct = new Contact(
                contact.getId(),
                contact.getFullname(),
                contact.getEmail(),
                contact.getMessage(),false,
                contact.getSendDate(),
                new Date(), account);
        dao.save(ct);
    }

    @Override
    public void deleteById(Integer id) {
         dao.deleteById(id);
    }
}

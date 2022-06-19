package com.clothes.repository.service;

import com.clothes.repository.Payment;
import com.clothes.repository.PaymentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    PaymentDAO dao;

    @Override
    public List<Payment> findAll() {
        return dao.findAll();
    }
}

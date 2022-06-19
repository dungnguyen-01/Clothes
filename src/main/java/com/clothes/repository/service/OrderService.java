package com.clothes.repository.service;

import com.clothes.repository.Order;
import com.clothes.service.cart.CartService;

import java.util.List;

public interface OrderService {
    List<Order> findAll();

    void create(Order order, CartService cartService);

    Order getById(Long id);

    void update(Order order);

    List<Order> findByRecipient(Integer id);

    List<Order> findByStatusPending();
}

package com.clothes.repository.service;

import com.clothes.repository.Order;
import com.clothes.repository.OrderDAO;
import com.clothes.repository.OrderDetail;
import com.clothes.repository.OrderDetailDAO;
import com.clothes.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderDAO dao;

    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Override
    public List<Order> findAll() {
        return dao.findAll();
    }

    @Transactional
    @Override
    public void create(Order order, CartService cartService) {
        dao.save(order);
        List<OrderDetail> orderDetails = cartService.getItems()
                .stream()
                .map(item -> {
                    return  new OrderDetail(item.getQty(),item.getSize(),item.getColor(),order,item.getProduct());
                }).toList();
        orderDetailDAO.saveAll(orderDetails);
    }

    @Override
    public Order getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public void update(Order order) {
        dao.save(order);
    }

    @Override
    public List<Order> findByRecipient(Integer id) {
        return dao.findByRecipient(id);
    }

    @Override
    public List<Order> findByStatusPending() {
        return dao.findByStatusSidOrderByCreateDateDesc(0);
    }

}

package com.clothes.service.cart;

import com.clothes.controller.productDTO;
import com.clothes.repository.Product;
import com.clothes.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;
import java.util.stream.Collectors;

@Service
@SessionScope
public class  CartServiceImpl implements CartService {
    @Autowired
    ProductDAO dao;

    Map<Integer, CartItem> map = new HashMap<>();

    @Override
    public Collection<CartItem> getItems() {
        return map.values();
    }

    @Override
    public void add(Integer id, Integer qty, String color, String size) {
        CartItem item = map.get(id);
        if (item == null){
            Product product = dao.getById(id);
            item = new CartItem(product,qty,color,size);
            map.put(id,item);
        }else {
            item.increase();
        }
    }

    @Override
    public void update(Integer id, Integer qty) {
        CartItem item = map.get(id);
        item.setQty(qty);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void delete(Integer id) {
        map.remove(id);
    }

    @Override
    public int getCount() {
        return this.getItems()
                .stream()
                .mapToInt(a -> a.getQty())
                .sum();
    }

    @Override
    public double getAmount() {
        return this.getItems()
                .stream()
                .mapToDouble(a -> a.getAmount())
                .sum();
    }

    @Override
    public List<productDTO> getAllItems() {
        List<productDTO> list = new ArrayList<>();
        for (CartItem item : this.getItems()) {
            productDTO dto = new productDTO();
            dto.setImage(item.getProduct().getImage());
            dto.setName(item.getProduct().getPname());
            dto.setTotal(item.getProduct().getPromotePrice());
            dto.setQty(item.getQty());
            list.add(dto);
        }
        return list;

    }
}

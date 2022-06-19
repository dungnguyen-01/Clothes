package com.clothes.service.cart;

import com.clothes.controller.productDTO;
import com.clothes.repository.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CartService {

    Collection<CartItem> getItems();

    void add(Integer id,Integer qty,String color, String size);

    void update(Integer id, Integer qty);

    void clear();

    void delete(Integer id);

    int getCount();

    double getAmount();

    List<productDTO> getAllItems();

}

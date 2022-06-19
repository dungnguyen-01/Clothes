package com.clothes.service.cart;

import com.clothes.repository.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private Product product;
    private Integer qty;
    private String color;
    private String size;


    public Double getAmount(){return this.qty * product.getPromotePrice();}
    public void increase(){ this.qty++;}


}

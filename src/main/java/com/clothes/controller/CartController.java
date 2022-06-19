package com.clothes.controller;

import com.clothes.repository.service.BlogService;
import com.clothes.service.cart.CartItem;
import com.clothes.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CartController {

    @Autowired
    CartService cartService;


    @RequestMapping("/cart/view")
    public String view(Model model){
        return "cart/index";
    }


    @RequestMapping("/cart/add/{id}/{qty}")
    public  String add(@PathVariable("id") Integer id, @PathVariable("qty")Optional<Integer> qtyOpt,
                       @RequestBody CartItem item){
        Integer qty = qtyOpt.orElse(1);
        String size = item.getSize();
        String color = item.getColor();
        cartService.add(id, qty,color,size);
        return "forward:/cart/info";
    }
    @RequestMapping("/cart/update/{id}/{qty}")
    public  String update(@PathVariable("id") Integer id,@PathVariable("qty") Integer qty){
        cartService.update(id,qty);
        return "forward:/cart/info";
    }

    @RequestMapping("/cart/delete/{id}")
    public  String delete(@PathVariable("id") Integer id){
        cartService.delete(id);
        return "forward:/cart/info";
    }

    @RequestMapping("/cart/clear")
    public  String clear(){
        cartService.clear();
        return "redirect:/cart/view";
    }

    @ResponseBody
    @RequestMapping("/cart/info")
    public Object info(){
        System.out.println("tha thanh cong r");
        System.out.println(

        );
        return Map.of("count",cartService.getCount(),"amount",cartService.getAmount()
                ,"shopping",cartService.getAllItems(), "totall",6);
    }
}



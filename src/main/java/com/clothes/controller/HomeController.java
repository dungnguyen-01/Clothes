package com.clothes.controller;

import com.clothes.repository.Blog;
import com.clothes.repository.Category;
import com.clothes.repository.Product;
import com.clothes.repository.service.BlogService;
import com.clothes.repository.service.CategoryService;
import com.clothes.repository.service.ProductService;
import com.clothes.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    ProductService productService;


    @RequestMapping({"/","/home/index"})
    public String index(Model model){
        List<Product> productAll = productService.findTop5All();
        List<Product> productMen = productService.findTop5Men(0);
        List<Product> productMenf = productService.findTop4Men(0);
        model.addAttribute("productMenf",productMenf);

        List<Product> productWomen = productService.findTop5Men(1);
        List<Product> productMenWomen = productService.findTop5Men(2);
        List<Product> productDiscount = productService.findTop5Discount();
        List<Product> productSpecial = productService.findTop5Special();
        List<Product> productNew = productService.findTop5New();
        model.addAttribute("products",productAll);
        model.addAttribute("productMen",productMen);
        model.addAttribute("productWomen",productWomen);
        model.addAttribute("productMenWomen",productMenWomen);
        model.addAttribute("productDiscount",productDiscount);
        model.addAttribute("productSpecial",productSpecial);
        model.addAttribute("productNew",productNew);

        return "/home/index";
    }

    @RequestMapping("about/info")
    public String about(Model model){
        return "contact/about";
    }


}

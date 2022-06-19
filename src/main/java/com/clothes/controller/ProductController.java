package com.clothes.controller;

import com.clothes.repository.*;
import com.clothes.repository.service.*;
import com.clothes.service.cart.CartService;
import com.clothes.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;
    @Autowired
    ProductColorService productColorService;
    @Autowired
    DetailProductSizeService detailProductSizeService;
    @Autowired
    ImageService imageService;
    @Autowired
    MailService mailService;


    @RequestMapping({"/product/list","/product/list/{n}"})
    public String index(Model model, @PathVariable("n") Optional<Integer> nOpt){
        Integer n = nOpt.orElse(0);
        Pageable pageable = PageRequest.of(n,12);
        Page<Product> page = productService.findAllProducrPage(pageable);
        model.addAttribute("page","list");
        model.addAttribute("products",page);

        return "/product/list";
    }

    @RequestMapping({"/product/special","/product/special/{n}"})
    public String special(Model model, @PathVariable("n") Optional<Integer> nOpt){
        Integer n = nOpt.orElse(0);
        Pageable pageable = PageRequest.of(n,12);
        Page<Product> page = productService.findBySpecialIsTrue(pageable);
        model.addAttribute("page","special");
        model.addAttribute("products",page);

        return "/product/list";
    }



    @RequestMapping({"/product/best","/product/best/{n}"})
    public String best(Model model, @PathVariable("n") Optional<Integer> nOpt){
        Integer n = nOpt.orElse(0);
        Pageable pageable = PageRequest.of(n,12);
        Page<Product> page = productService.findByBestSeller(pageable);
        model.addAttribute("page","best");
        model.addAttribute("products",page);
        return "/product/list";
    }


    @RequestMapping({"/product/discount","/product/discount/{n}"})
    public String discount(Model model, @PathVariable("n") Optional<Integer> nOpt){
        Integer n = nOpt.orElse(0);
        Pageable pageable = PageRequest.of(n,12);
        Page<Product> page = productService.findByDiscount(pageable);
        model.addAttribute("page","discount");
        model.addAttribute("products",page);

        return "/product/list";
    }

    @RequestMapping({"/product/search","/product/search/{n}"})
    public String search(Model model, @RequestParam("keyword") Optional<String> keyword,
                         @PathVariable("n") Optional<Integer> n){
        Pageable pageable = PageRequest.of(n.orElse(0),12);
        Page<Product> page = productService.findByKeywords(keyword.orElse(""),pageable);
        model.addAttribute("page","search");
        model.addAttribute("products",page);

        return "/product/list";
    }

    @RequestMapping({"/product/category/{cid}","/product/category/{cid}/{n}"})
    public String category(Model model, @PathVariable("cid")Integer id,
                           @PathVariable("n") Optional<Integer> nOpt){
        Integer n = nOpt.orElse(0);
        Pageable pageable = PageRequest.of(n,12);
        Page<Product> page = productService.findByCategory(id,pageable);
        model.addAttribute("page","category/"+id);
        model.addAttribute("products",page);

        return "/product/list";
    }

    @RequestMapping("/product/detail/{pid}")
    public String detail(Model model, @PathVariable("pid")Integer id){
       Product product = productService.getById(id);
       product.setSeen(product.getSeen()+1);
       productService.updateSeen(product);
       model.addAttribute("product",product);

        return "/product/detail";
    }

    @Autowired
    ShareService shareService;
    @ResponseBody
    @RequestMapping("/product/send-share-mail")
    public void sharedMail(@RequestBody Share share){
        shareService.create(share);
        mailService.sendShare(share);
        System.out.println(share.toString());
    }



    @ModelAttribute("categories")
    public List<Category> getCategory(){ return categoryService.findAll();}

}

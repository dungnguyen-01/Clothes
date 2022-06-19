package com.clothes.controller;

import com.clothes.repository.Blog;
import com.clothes.repository.ProductDAO;
import com.clothes.repository.service.BlogService;
import com.clothes.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    ProductDAO productDAO;


    @RequestMapping({"/list","list/{n}"})
    public String list(Model model, @PathVariable("n")Optional<Integer> nopt){
        Integer n = nopt.orElse(0);
        Pageable pageable = PageRequest.of(n,5, Sort.by("createDate").descending());
        Page<Blog> page = blogService.findAllBlogPage(pageable);
        List<Blog> listTop3Seen = blogService.findTop3ByOrderBySeen();
        model.addAttribute("products", productDAO.findTop3ByOrderBySeenDesc());
        model.addAttribute("sees" ,listTop3Seen);
        model.addAttribute("page",page);
        return "blog/list";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model,@PathVariable("id") Integer id){
        Blog blog = blogService.getById(id);
        blog.setSeen(blog.getSeen()+1);
        blogService.create(blog);
        List<Blog> listTop3Seen = blogService.findTop3ByOrderBySeen();
        model.addAttribute("products", productDAO.findTop3ByOrderBySeenDesc());
        model.addAttribute("sees" ,listTop3Seen);
        model.addAttribute("blog",blog);
        return "blog/detail";
    }

}

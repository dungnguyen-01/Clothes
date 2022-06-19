package com.clothes.admin.controller;

import com.clothes.repository.*;
import com.clothes.repository.service.*;
import com.clothes.security.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
public class HomeAController {
    @Autowired
    OrderService orderService;
    @Autowired
    StatusService statusService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    AccountService accountService;
    @Autowired
    ProductService productService;

    @RequestMapping("/403")
    public String defind() {
    return "/403";
    }

    @RequestMapping("/admin/home/index")
    public String index(Model model){
        List<Order> orders = orderService.findByStatusPending();
        model.addAttribute("items",orders);

        List<Account> accounts = accountService.findAll();
        model.addAttribute("accounts",accounts);

        List<Product> products = productService.findAll();
        model.addAttribute("products",products);
        return "/admin/home/index";
    }

    @RequestMapping("/admin/home/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id){
        Order order = orderService.getById(id);
        model.addAttribute("item", order);
        model.addAttribute("hid",order.getStatus().getSid());
        return "/admin/home/detail-modal";
    }

    @RequestMapping("/admin/home/update")
    public String update(Model model, Authentication auth, @ModelAttribute("item") Order order) {
        UserDetailImpl userDetail = (UserDetailImpl) auth.getPrincipal();
        Account account  =  userDetail.getAccount();
        order.setHandlerName(account);
        order.setUpdateDate(new Date());
        orderService.update(order);
        model.addAttribute("nofi","nofi");
        return "redirect:/admin/home/index";
    }

    @ModelAttribute("statuses")
    public List<Status> statuses(){
        return statusService.findAll();
    }

    @ModelAttribute("payments")
    public List<Payment> payments(){
        return paymentService.findAll();
    }
}

package com.clothes.admin.controller;


import com.clothes.repository.Account;
import com.clothes.repository.Order;
import com.clothes.repository.Payment;
import com.clothes.repository.Status;
import com.clothes.repository.service.OrderService;
import com.clothes.repository.service.PaymentService;
import com.clothes.repository.service.StatusService;
import com.clothes.security.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class OrderAController {

    @Autowired
    OrderService orderService;
    @Autowired
    StatusService statusService;
    @Autowired
    PaymentService paymentService;

    @RequestMapping("/reset")
    public String reset(Model model){
        model.addAttribute("item", new Order());
        model.addAttribute("edit",true);
        return "redirect:/admin/order/index";
    }

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("item", new Order());
        model.addAttribute("edit",true);
        return  this.forward(model);
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id){
        Order order = orderService.getById(id);
        model.addAttribute("item", order);
        model.addAttribute("edit",false);
        model.addAttribute("hid",order.getStatus().getSid());
        return  this.forward(model);
    }

    @PostMapping("/update")
    public String update(Model model, Authentication auth, @ModelAttribute("item") Order order){
        UserDetailImpl userDetail = (UserDetailImpl) auth.getPrincipal();
        Account account  =  userDetail.getAccount();
        order.setHandlerName(account);
        order.setUpdateDate(new Date());
        orderService.update(order);
        model.addAttribute("message","Thực hiện thành công");
        model.addAttribute("edit",false);
        return this.forward(model);
    }
    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id){
        Order order = orderService.getById(id);
        model.addAttribute("item", order);
        model.addAttribute("edit",false);
        model.addAttribute("hid",order.getStatus().getSid());
        return "/admin/order/detail-modal";
    }


    public String forward(Model model){
        List<Order> orders = orderService.findAll();
        model.addAttribute("items",orders);
        return "/admin/order/index";
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

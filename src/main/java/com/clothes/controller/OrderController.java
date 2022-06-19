package com.clothes.controller;

import com.clothes.repository.*;
import com.clothes.repository.service.AccountService;
import com.clothes.repository.service.OrderService;
import com.clothes.repository.service.ProductService;
import com.clothes.security.UserDetailImpl;
import com.clothes.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {
    @Autowired
    AccountService accountService;
    @Autowired
    CartService cartService;

    @RequestMapping("/order/checkout")
    public String checkout(Model model, Authentication auth){
        UserDetailImpl userDetail = (UserDetailImpl) auth.getPrincipal();
        Account account = userDetail.getAccount();

        Order order = new Order();
        order.setRecipient(account);
        order.setMobile(account.getMobile());
        order.setCreateDate(new Date());
        order.setAmount(cartService.getAmount());

        model.addAttribute("item", order);
        model.addAttribute("amount", cartService.getAmount());

        return "/order/checkout";
    }


    @Autowired
    StatusDAO statusDAO;
    @Autowired
    PaymentDAO paymentDAO;
    @Autowired
    OrderService orderService;
    @RequestMapping("/order/purchase")
    public String purchase(Model model,Authentication auth,
                           @ModelAttribute("item") Order order,
                           @RequestParam("city") String city,
                           @RequestParam("district") String district,
                           @RequestParam("ward") String ward,
                           @RequestParam("payment") Integer payment){
        try {
            UserDetailImpl userDetail = (UserDetailImpl) auth.getPrincipal();
            Account account = userDetail.getAccount();
            String address =  ward + ", " +district +", " +city;
            order.setRecipient(account);
            order.setAddress(address);
            order.setCreateDate(new Date());
            order.setStatus(statusDAO.getById(0));
            order.setPayment(paymentDAO.getById(payment));
            orderService.create(order, cartService);

            model.addAttribute("nofiordersuccess", "nofiordersuccess");
            cartService.clear();

            return "redirect:/order/detail/" + order.getOid();
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("nofiordererror", "nofiordererror");
            return "/order/checkout";

        }
    }

    @RequestMapping("/order/detail/{id}")
    public String detail(Model model,@PathVariable("id") Long id){
        Order order = orderService.getById(id);
        model.addAttribute("item", order);

        return "/order/detail";
    }

    @RequestMapping("/order/cancel/{id}")
    public String cancel(Model model,@PathVariable("id") Long id){
        Order order = orderService.getById(id);
        order.setStatus(statusDAO.getById(-1));
        orderService.update(order);
        return "redirect:/order/detail/"+id;
    }
    @RequestMapping("/order/list")
    public String list(Model model,Authentication auth){
        UserDetailImpl userDetail = (UserDetailImpl) auth.getPrincipal();
        Account account = userDetail.getAccount();
        List<Order> orders = orderService.findByRecipient(account.getId());
        model.addAttribute("orders",orders);
        return "/order/list";
    }

    @Autowired
    ProductService productService;
    @RequestMapping({"/order/items-purchased","/order/items-purchased/{n}"})
    public String itemsPurchased(Model model,Authentication auth,@PathVariable("n") Optional<Integer> nOpt) {
        UserDetailImpl userDetails = (UserDetailImpl) auth.getPrincipal();
        Account account = userDetails.getAccount();
        Integer n = nOpt.orElse(0);
        Pageable pageable = PageRequest.of(n,12);
        Page<Product> page = productService.findById(account.getId(),pageable);
        model.addAttribute("page","items-purchased");
        model.addAttribute("products",page);
        return"/product/list";
    }

    @ResponseBody
    @RequestMapping("/api/province")
    public Object province() {
        try {
            File resource = new ClassPathResource("local.json").getFile();
            String text = new String(Files.readAllBytes(resource.toPath()));
            return text;
        } catch (IOException e) {
            e.printStackTrace();
            return "Không có kết quả trả về";
        }
    }



}

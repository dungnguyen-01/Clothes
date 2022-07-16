package com.clothes.controller;

import com.clothes.repository.Account;
import com.clothes.repository.Order;
import com.clothes.repository.PaymentDAO;
import com.clothes.repository.StatusDAO;
import com.clothes.repository.service.OrderService;
import com.clothes.repository.service.PaymentServiceImpl;
import com.clothes.security.UserDetailImpl;
import com.clothes.service.cart.CartService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class PaymentController {
    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @Autowired
    PaymentServiceImpl paymentService;

    @PostMapping("/pay")
    public String payment(@ModelAttribute("order") Order order) {
        try {
            Double usd = order.getAmount() * 0.000043;

            Payment payment = paymentService.createPayment(usd, "USD", "paypal",
                    "sale", order.getNotes(), "http://localhost:8080/" + CANCEL_URL,
                    "http://localhost:8080/" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }
        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "redirect:/";
    }


    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @Autowired
    StatusDAO statusDAO;
    @Autowired
    PaymentDAO paymentDAO;
    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;
    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
                             Model model, Authentication auth,
                             @ModelAttribute("item") Order order,
                             @RequestParam("city") String city,
                             @RequestParam("district") String district,
                             @RequestParam("ward") String ward,
                             @RequestParam("payment") Integer pay) {
        try {
            UserDetailImpl userDetail = (UserDetailImpl) auth.getPrincipal();
            Account account = userDetail.getAccount();
            String address =  ward + ", " +district +", " +city;
            order.setRecipient(account);
            order.setAddress(address);
            order.setCreateDate(new Date());
            order.setStatus(statusDAO.getById(0));
            order.setPayment(paymentDAO.getById(pay));
            orderService.create(order, cartService);

            model.addAttribute("nofiordersuccess", "nofiordersuccess");
            cartService.clear();

            Payment payment = paymentService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
            return "redirect:/order/detail/" + order.getOid();

        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

}

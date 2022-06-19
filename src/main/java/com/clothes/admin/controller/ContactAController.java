package com.clothes.admin.controller;

import com.clothes.repository.Account;
import com.clothes.repository.Contact;
import com.clothes.repository.service.ContactService;
import com.clothes.security.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/contact")
public class ContactAController {

    @Autowired
    ContactService contactService;

    @RequestMapping("/list")
    public String list(Model model){

        model.addAttribute("edit", true);
        return this.forward(model);
    }

    @RequestMapping("/update/{id}")
    public String update(Model model,@PathVariable("id") Integer id, Authentication auth){
        try {
            contactService.updateContact(id,auth);
            System.out.println("update thanh cong");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("thực hiện bi lôi");
        }
        model.addAttribute("edit", true);
        return this.forward(model);
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
            contactService.deleteById(id);
            System.out.println("delete thanh cong");
        return "/admin/contact/list";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model,@PathVariable("id") Integer id){
        Contact contact = contactService.getById(id);
        model.addAttribute("item",contact);
        model.addAttribute("edit", true);
        return "/admin/contact/detail-modal";
    }

    public String forward(Model model){
        List<Contact> contacts = contactService.finAll();
        model.addAttribute("items", contacts);
        return "/admin/contact/list";
    }




}

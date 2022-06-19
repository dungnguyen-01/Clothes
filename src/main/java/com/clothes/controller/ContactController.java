package com.clothes.controller;

import com.clothes.repository.Contact;
import com.clothes.repository.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    @PostMapping("/send")
    public String send(Model model,
                       @RequestParam("fullname") String fullname,
                       @RequestParam("email") String email,
                       @RequestParam("message") String message
                       ){

        Contact contact = new Contact(fullname,email,message,true,new Date());
        contactService.create(contact);
        model.addAttribute("contactnofi","contactnofi");

        return "/contact/about";
    }
}

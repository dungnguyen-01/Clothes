package com.clothes.admin.controller;

import com.clothes.repository.Account;
import com.clothes.repository.Role;
import com.clothes.repository.service.AccountService;
import com.clothes.repository.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/account")
public class AccountAController {
    @Autowired
    AccountService accountService;
    @Autowired
    RoleService roleService;

    @RequestMapping("/reset")
    public String reset (Model model){
        model.addAttribute("edit", false);
        return "redirect:/admin/account/index";
    }

    @GetMapping("/edit/{id}")
    public  String edit(Model model, @PathVariable("id")Integer id){
        Account account = accountService.getById(id);
        model.addAttribute("item",account);
        model.addAttribute("edit", false);
        return  this.forward(model);
    }

    @GetMapping("/detail/{id}")
    public  String detail(Model model, @PathVariable("id")Integer id){
        Account account = accountService.getById(id);
        model.addAttribute("item",account);
        model.addAttribute("edit", true);
        return "/admin/account/detail-modal";
    }

    @PostMapping("/create")
    public  String create(Model model, @ModelAttribute("item") Account account){
        accountService.create(account);
        model.addAttribute("message","Create successful");
        model.addAttribute("edit", false);
        return this.forward(model);
    }

    @PostMapping("/update")
    public  String update(Model model, @ModelAttribute("item") Account account){
        accountService.update(account);
        System.out.println("đay la role: "+ account);
        model.addAttribute("message","Update successful");
        model.addAttribute("edit", false);
        return this.forward(model);
    }

    @RequestMapping("/index")
    public String index (Model model){
        model.addAttribute("item",new Account());
        model.addAttribute("edit", true);
        return this.forward(model);
    }

    @ResponseBody
    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id")Integer id){
        try {
            accountService.deleteById(id);
            model.addAttribute("item", new Account());
            model.addAttribute("edit",true);
            System.out.println("test case");
            return  this.forward(model);
        }catch (Exception e){ throw new RuntimeException("Thực hiện bị lỗi: ",e);}

    }

    @ModelAttribute("roles")
    public  List<Role> roles(){
        return roleService.findAll();
    }


    public  String forward(Model model){
        List<Account> accounts = accountService.findAll();
        model.addAttribute("items", accounts);
        return "/admin/account/index";
    }

    @RequestMapping("xin")
    public String name(){
        return "";
    }

}

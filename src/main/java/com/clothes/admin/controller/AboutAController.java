package com.clothes.admin.controller;

import com.clothes.repository.About;
import com.clothes.repository.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/about")
public class AboutAController {
    @Autowired
    AboutService aboutService;

    @RequestMapping("/reset")
    public  String reset(Model model){
        model.addAttribute("edit", false);
        return "redirect:/admin/about/list";
    }

    @RequestMapping("/list")
    public  String index(Model model){
        model.addAttribute("item", new About());
        model.addAttribute("edit", true);
        return this.forward(model);
    }

    @RequestMapping("/create")
    public String create(Model model, @ModelAttribute("item") About about) {
        model.addAttribute("edit", false);
        aboutService.create(about);
        model.addAttribute("message","Tạo thành công");
        return this.forward(model);
    }

    @RequestMapping("/edit/{id}")
    public String create(Model model, @PathVariable("id") Integer id) {

        About about = aboutService.getById(id);
        model.addAttribute("item", about);
        model.addAttribute("edit", false);
        return this.forward(model);
    }

    @RequestMapping("/update")
    public String update(Model model, @ModelAttribute("item") About about) {

        aboutService.create(about);
        model.addAttribute("edit", false);
        return this.forward(model);
    }

    public String forward(Model model){
        List<About> abouts = aboutService.findAll();
        model.addAttribute("items",abouts);
        return "/admin/about/list";
    }

}

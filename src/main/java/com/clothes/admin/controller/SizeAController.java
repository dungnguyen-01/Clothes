package com.clothes.admin.controller;

import com.clothes.repository.Category;
import com.clothes.repository.Size;
import com.clothes.repository.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/size")
public class SizeAController {

    @Autowired
    SizeService sizeService;

    @RequestMapping("/reset")
    public String reset(Model model){
        model.addAttribute("edit",false);
        return "redirect:/admin/size/index";
    }

    @RequestMapping("/index")
    public String index(Model model, @ModelAttribute("size") Size size){
        model.addAttribute("size",size);
        model.addAttribute("edit",true);
        return this.forward(model);
    }

    @PostMapping("/create")
    public  String create(Model model, @Validated @ModelAttribute("size") Size size, BindingResult result,
                          @RequestParam("sname") String name){
        if (result.hasErrors()){
            model.addAttribute("message", "Please fix the errors!");
        }else {
            if (sizeService.checkSizeName(name) > 0){
                model.addAttribute("message","Ten da ton tai cui long nhap ten khac");
            }else {
                sizeService.create(size);
                model.addAttribute("message","Create size successfully");
            }
        }
        model.addAttribute("edit", false);
        return this.forward(model);
    }
    @PostMapping("/update")
    public  String update(Model model,@Validated @ModelAttribute("size") Size size, BindingResult result,
                          @RequestParam("sname") String name){
        if (result.hasErrors()){
            model.addAttribute("message", "Please fix the errors!");
        }else {
                sizeService.create(size);
                model.addAttribute("message","Update SIZE successfully");
        }
        model.addAttribute("edit", false);
        return this.forward(model);
    }

    @RequestMapping("/edit/{sid}")
    public  String edit(Model model, @PathVariable("sid") Integer id){
        Size size = sizeService.edit(id);
        model.addAttribute("size",size);
        model.addAttribute("edit", false);
        return this.forward(model);
    }
    @ResponseBody
    @RequestMapping("/delete/{sid}")
    public  String delete(Model model, @PathVariable("sid") Integer id){
        sizeService.delete(id);
        model.addAttribute("message","Delete size successfully");
        model.addAttribute("size", new Size());
        model.addAttribute("edit", true);
        return this.forward(model);
    }

    public String forward(Model model){
        List<Size> sizes = sizeService.findAll();
        model.addAttribute("sizes",sizes);
        return "/admin/size/index";
    }

}

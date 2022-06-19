package com.clothes.admin.controller;

import com.clothes.repository.Category;
import com.clothes.repository.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CategoryAController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/category/reset")
    public  String reset(Model model){
         model.addAttribute("edit", false);
         return "redirect:/admin/category/index";
    }


    @RequestMapping("/category/index")
        public  String index(Model model){
        model.addAttribute("item", new Category());
        model.addAttribute("edit", true);
        return this.forward(model);
    }

    @PostMapping("/category/create")
    public  String create(Model model, @Validated @ModelAttribute("item") Category item, BindingResult result,
                          @RequestParam("cname") String name){
        if (result.hasErrors()){
            model.addAttribute("message", "Please fix the errors!");
        }else {
            if (categoryService.checkCategoryName(name) > 0){
                model.addAttribute("message","Ten da ton tai cui long nhap ten khac");
            }else {
                categoryService.create(item);
                model.addAttribute("nofi","nofi");
                model.addAttribute("message","Create category successfully");
        }
        }
        model.addAttribute("edit", false);
        return this.forward(model);
    }
    @PostMapping("/category/update")
    public  String update(Model model,@Validated @ModelAttribute("item") Category item, BindingResult result,
        @RequestParam("cname") String name){
            if (result.hasErrors()){
                model.addAttribute("message", "Please fix the errors!");
            }else {
                if (categoryService.checkCategoryName(name) > 0){
                    model.addAttribute("message","Ten da ton tai vui long nhap ten khac");
                }else {
                    categoryService.create(item);
                    model.addAttribute("message","Update category successfully");
                }
            }
        model.addAttribute("edit", false);
        return this.forward(model);
    }

    @RequestMapping("/category/edit/{cid}")
    public  String edit(Model model, @PathVariable("cid") Integer id){
        Category category = categoryService.edit(id);
        model.addAttribute("item",category);
        model.addAttribute("edit", false);
        return this.forward(model);
    }

    @RequestMapping("/category/detail/{cid}")
    public  String detail(Model model, @PathVariable("cid") Integer id){
        Category category = categoryService.edit(id);
        model.addAttribute("item",category);
        model.addAttribute("edit", true);
        return "/admin/category/_detail";
    }

    @ResponseBody
    @RequestMapping("/category/delete/{cid}")
    public  String delete(Model model, @PathVariable("cid") Integer id){
        categoryService.delete(id);
        model.addAttribute("message","Delete category successfully");
        model.addAttribute("item", new Category());
        model.addAttribute("edit", true);
        return this.forward(model);
    }

    String forward(Model model) {
        List<Category> items = categoryService.findAll();
        model.addAttribute("items", items);
        return "admin/category/index";
    }
}

package com.clothes.admin.controller;

import com.clothes.repository.Account;
import com.clothes.repository.Blog;
import com.clothes.repository.service.BlogService;
import com.clothes.security.UserDetailImpl;
import com.clothes.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogAController {
    @Autowired
    BlogService blogService;
    @Autowired
    UploadService uploadService;

    @RequestMapping("/blog/reset")
    public  String reset(Model model){
        model.addAttribute("edit", false);
        return "redirect:/admin/blog/list";
    }

    @RequestMapping("/blog/list")
    public String list(Model model){
        Blog blog = new Blog();
        model.addAttribute("item",blog);
        model.addAttribute("edit", true);
        return  this.forward(model);
    }
    @RequestMapping("/blog/create")
    public String create(Model model, @Validated @ModelAttribute("item") Blog blog,BindingResult result,
                         @RequestParam("title") String title,
                         @RequestParam("seen") int seen,
                         @RequestParam("author") String author,
                         @RequestParam("descr") String descr,
                         @RequestParam("image_file")MultipartFile imageFile
                         ){

        try {
            float fileSizeMegabytes = imageFile.getSize() / 1_000_000f;
        if (result.hasErrors()){
            model.addAttribute("message", "Vui lòng sửa lỗi bên dưới.");
            model.addAttribute("validateImageAndSize", "Vui lòng chọn ảnh.");
        } else if (imageFile.isEmpty()){
            model.addAttribute("validateImageAndSize", "Vui lòng chọn ảnh.");
        }
        else {
            if(blogService.checkBlogTitle(blog.getTitle()) > 0){
                model.addAttribute("title", "Tiêu đề bị trùng lặp.");
            }else {
                    if (fileSizeMegabytes > 5.0f) {
                        model.addAttribute("message", "Vui lòng chọn ảnh có kích thước nhỏ hơn.");
                    }else {
                        String image = uploadService.save(imageFile);
                        Blog blogCreate = new Blog(title,author,seen,image,descr,date());
                        blogService.create(blogCreate);
                        model.addAttribute("message","Tạo thành công.");
                    }
            }
        }
        model.addAttribute("edit", false);
        return  this.forward(model);
        }catch (Exception e){ throw new RuntimeException("Thực hiện bị lỗi: ",e);}
    }


    @RequestMapping("/blog/update")
    public String update(Model model, @Validated @ModelAttribute("item") Blog blog,
                         @RequestParam("title") String title,
                         @RequestParam("seen") int seen,
                         @RequestParam("author") String author,
                         @RequestParam("descr") String descr,
                         @RequestParam("image") String image,
                         @RequestParam("createDate") String createDate,
                         @RequestParam("image_file")MultipartFile imageFile,
                         BindingResult result){

        if (result.hasErrors()){
            model.addAttribute("message", "Vui lòng sửa lỗi bên dưới.");
            model.addAttribute("validateImageAndSize", "Vui lòng chọn ảnh.");
        }else {
            if(blogService.checkBlogTitle(title) > 1){
                model.addAttribute("title", "Tiêu đề bị trùng lặp.");
            }else {
                if (!imageFile.isEmpty()){
                    System.out.println("anh moi"+ blog.getImage());
                    uploadService.delete(image);

                    String imageUpdate = uploadService.save(imageFile);
                    Blog blogCreate = new Blog(blog.getId(),title,author,seen,imageUpdate,descr,createDate,date());
                    blogService.create(blogCreate);

                }else {
                    Blog blogCreate = new Blog(blog.getId(),title,author,seen,image,descr,createDate,date());
                    blogService.create(blogCreate);
                }
                model.addAttribute("message","Sửa thành công.");
                model.addAttribute("edit", false);
            }
        }
        return  this.forward(model);
    }

    @RequestMapping("blog/edit/{id}")
    public String  edit(Model model,@PathVariable("id") int id){
        Blog blog = blogService.getById(id);
        model.addAttribute("item",blog);
        model.addAttribute("edit", false);
        return  this.forward(model);
    }

    @RequestMapping("blog/detail/{id}")
    public String  detail(Model model,@PathVariable("id") int id){
        Blog blog = blogService.getById(id);
        model.addAttribute("item",blog);
        model.addAttribute("edit", true);
        return "/admin/blog/detail-modal";
    }


    @ResponseBody
    @RequestMapping("blog/delete/{id}")
    public void  delete(@PathVariable("id") int id){
        blogService.deleteById(id);
    }

    public String forward(Model model){
        List<Blog> list = blogService.findAll();
        model.addAttribute("items",list);
        return "/admin/blog/list";
    }

    public static String date(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String createDate = myDateObj.format(myFormatObj);
        return  createDate;
    }
}

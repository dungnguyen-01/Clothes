package com.clothes.admin.controller;

import com.clothes.repository.*;
import com.clothes.repository.service.*;
import com.clothes.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
public class ProductAController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    SizeService sizeService;
    @Autowired
    ProductService productService;
    @Autowired
    UploadService  uploadService;
    @Autowired
    ImageService imageService;
    @Autowired
    DetailProductSizeService detailProductSizeService;
    @Autowired
    ColorService colorService;


    @RequestMapping("/reset")
    public  String reset(Model model){
        model.addAttribute("edit",false);
        return "redirect:/admin/product/index";
    }

    @RequestMapping("/index")
    public  String index(Model model){
        model.addAttribute("item", new Product());
        model.addAttribute("edit",true);
        return this.forward(model);
    }

    @RequestMapping("/edit/{pid}")
    public  String edit(Model model,@PathVariable("pid") Integer id ){
        List<Image> images = imageService.findByProductId(id);
        Product product = productService.getById(id);
        model.addAttribute("imageList", images);
        model.addAttribute("item", product);
        model.addAttribute("edit",true);
        return this.forward(model);
    }

    @RequestMapping("/detail/{pid}")
    public  String detail(Model model,@PathVariable("pid") Integer id ){
        List<Image> images = imageService.findByProductId(id);
        Product product = productService.getById(id);
        model.addAttribute("imageList", images);
        model.addAttribute("item", product);
        model.addAttribute("edit",false);
        return "/admin/product/detail-modal";
    }

    @PostMapping ("/create")
    public  String create(Model model,@Validated @ModelAttribute("item") Product product, BindingResult result,
                          @RequestPart("image_file") MultipartFile imageFile,
                          @RequestPart("image_file_desc") List<MultipartFile> imageFileDesc,
                          @RequestParam("sizeIds") Optional<List<String>> sizeIds,
                          @RequestParam("colorIds") Optional<List<String>> colorIds
                         ){
        try {
            if (result.hasErrors()) {
                model.addAttribute("message", "Vui l??ng s???a l???i b??n d?????i.");
                model.addAttribute("validateImageAndSize", "Vui l??ng ch???n.");
            }
            else if (sizeIds.isEmpty() && imageFile.isEmpty() && colorIds.isEmpty()) {
                model.addAttribute("validateImageAndSize", "Vui l??ng ch???n.");
            }
            else {
                if (productService.checkProductName(product.getPname()) > 0){
                    model.addAttribute("pname", "T??n s???n ph???m ???? t???n t???i.");
                }else {
                    if (!imageFile.isEmpty()) {
                        String photo = uploadService.save(imageFile);
                        product.setImage(photo);
                    }
                    productService.create(product, sizeIds.orElse(List.of()),colorIds.orElse(List.of()),imageFileDesc);
                    model.addAttribute("message","Create successful");
                }
            }
            model.addAttribute("edit",false);
            return this.forward(model);
        }catch (Exception e){ throw new RuntimeException("Th???c hi???n b??? l???i: ",e);}

    }

    @PostMapping ("/update")
    public  String update(Model model,@Validated @ModelAttribute("item") Product product,BindingResult result,
                          @RequestPart("image_file") MultipartFile imageFile,
                          @RequestParam("image_file_desc")  List<MultipartFile> imageFileDesc,
                          @RequestParam("sizeIds") Optional<List<String>> sizeIds,
                          @RequestParam("colorIds") Optional<List<String>> colorIds
                          ) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("message", "Vui l??ng s???a l???i b??n d?????i.");
            } else if (sizeIds.isEmpty() && colorIds.isEmpty()) {
                model.addAttribute("validateImageAndSize", "Vui l??ng ch???n.");
            } else {
                if (productService.checkProductName(product.getPname()) > 1) {
                    model.addAttribute("pname", "T??n s???n ph???m ???? t???n t???i.");
                } else {
                    if (!imageFile.isEmpty()) {
                        uploadService.delete(product.getImage());
                        String photo = uploadService.save(imageFile);
                        product.setImage(photo);
                        productService.update(product, sizeIds.orElse(List.of()),colorIds.orElse(List.of()), imageFileDesc);
                    }else {
                        productService.update(product, sizeIds.orElse(List.of()),colorIds.orElse(List.of()), imageFileDesc);
                    }
                    model.addAttribute("message","S???a th??nh c??ng.");
                    model.addAttribute("edit", false);
                }
            }
            return this.forward(model);
        }catch (Exception e){ throw new RuntimeException("Th???c hi???n b??? l???i: ",e);}

    }

    @ResponseBody
    @RequestMapping("/delete/{pid}")
    public String delete(Model model, @PathVariable("pid") Integer id){
        try {
            productService.deleteById(id);
            model.addAttribute("item", new Product());
            model.addAttribute("edit",true);
            return  this.forward(model);
        }catch (Exception e){ throw new RuntimeException("Th???c hi???n b??? l???i: ",e);}

    }


    public String forward(Model model){
        List<Product> list = productService.findAll();
        model.addAttribute("items",list);
        return "/admin/product/index";
    }

    @ResponseBody
    @RequestMapping("/image/delete/{iid}")
    public  String deleteImage(Model model, @PathVariable("iid") Integer id){
        Image image = imageService.getById(id);
        //uploadService.delete(image.getIname());
        imageService.deleteById(id);
        return this.forward(model);
    }

    @ModelAttribute("categories")
    public List<Category> getCategories(){
        return categoryService.findAll();
    }
    @ModelAttribute("sizeShirt")
    public  List<Size> getSizesShirt(){
        return sizeService.findAllByShirt();
    }
    @ModelAttribute("sizeTrousers")
    public  List<Size> getSizesTrousers(){
        return sizeService.findAllByTrousers();
    }
    @ModelAttribute("colors")
    public  List<Color> getColors(){ return colorService.findAll();}

}

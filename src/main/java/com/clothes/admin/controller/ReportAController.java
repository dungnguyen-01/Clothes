package com.clothes.admin.controller;

import com.clothes.repository.Report;
import com.clothes.repository.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
public class ReportAController {

    @Autowired
    ReportService reportService;

    @RequestMapping("/admin/report/inventory")
    public String inventory() {
        return "admin/report/inventory";
    }

    @ResponseBody
    @RequestMapping({"/api/product/like", "/api/product/like/{size}"})
    public List<Report> getTopLikes(@PathVariable("size") Optional<Integer> sizeOpts) {
        int size = sizeOpts.orElse(5);
        return reportService.getTopLikes(size);
    }

    @ResponseBody
    @RequestMapping({"/api/product/share", "/api/product/share/{size}"})
    public List<Report> getTopShares(@PathVariable("size") Optional<Integer> sizeOpts) {
        int size = sizeOpts.orElse(5);
        return reportService.getTopShares(size);
    }

    @ResponseBody
    @RequestMapping("/api/inventory")
    public List<Report> getInventory() {
        return reportService.getInventoryByCategory();
    }
}

package com.clothes.repository.service;

import com.clothes.repository.Report;
import com.clothes.repository.ReportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{
    @Autowired
    ReportDAO dao;

    @Override
    public List<Report> getTopLikes(int size) {
        Pageable pageable = (Pageable) PageRequest.of(0, size);
        return dao.getTopLikes(pageable);
    }

    @Override
    public List<Report> getTopShares(int size) {
        Pageable pageable = PageRequest.of(0, size);
        return dao.getTopShares(pageable);
    }

    @Override
    public List<Report> getInventoryByCategory() {
        return dao.getInventoryByCategory();
    }
}

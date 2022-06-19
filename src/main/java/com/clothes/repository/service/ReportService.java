package com.clothes.repository.service;

import com.clothes.repository.Report;

import java.util.List;

public interface ReportService {
    List<Report> getTopLikes(int size);

    List<Report> getTopShares(int size);

    List<Report> getInventoryByCategory();
}

package com.clothes.repository.service;

import com.clothes.repository.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface BlogService {
    List<Blog> findAll();


    Integer checkBlogTitle(String title);


    void create(Blog blogCreate);

    void deleteById(int id);

    Blog getById(int id);

    Page<Blog> findAllBlogPage(Pageable pageable);

    List<Blog> findTop3ByOrderBySeen();
}

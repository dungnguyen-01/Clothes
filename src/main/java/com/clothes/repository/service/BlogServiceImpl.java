package com.clothes.repository.service;

import com.clothes.repository.Blog;
import com.clothes.repository.BlogDAO;
import com.clothes.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    BlogDAO dao;
    @Autowired
    UploadService uploadService;

    @Override
    public List<Blog> findAll() {
        return dao.findAll();
    }

    @Override
    public Integer checkBlogTitle(String title) {
        return dao.checkBlogTitle(title);
    }

    @Override
    public void create(Blog blogCreate) {
        dao.save(blogCreate);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        Blog blog = dao.getById(id);
        uploadService.delete(blog.getImage());
        dao.deleteById(id);
    }

    @Override
    public Blog getById(int id) {
        return dao.getById(id);
    }

    @Override
    public Page<Blog> findAllBlogPage(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public List<Blog> findTop3ByOrderBySeen() {
        return dao.findTop3ByOrderBySeenDesc();
    }
}

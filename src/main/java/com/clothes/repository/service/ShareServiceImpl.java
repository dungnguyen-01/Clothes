package com.clothes.repository.service;

import com.clothes.repository.Share;
import com.clothes.repository.ShareDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareServiceImpl implements  ShareService{
    @Autowired
    ShareDAO dao;

    @Override
    public void create(Share share) {
        dao.save(share);
    }
}

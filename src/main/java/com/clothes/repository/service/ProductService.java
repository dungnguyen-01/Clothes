package com.clothes.repository.service;

import com.clothes.repository.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    void create(Product product, List<String> sizeIds, List<String> colorIds, List<MultipartFile> imageFileDesc);

    Product getById(Integer id);

    void update(Product product, List<String> sizeIds, List<String> colorIds, List<MultipartFile> imageFileDesc);

    void deleteById(Integer id);

    Integer checkProductName(String pname);

    //client

    Page<Product> findAllProducrPage(Pageable pageable);

    Page<Product> findBySpecialIsTrue(Pageable pageable);

    Page<Product> findByDiscount(Pageable pageable);

    Page<Product> findByKeywords(String keywords, Pageable pageable);

    Page<Product> findByCategory(Integer id, Pageable pageable);

    List<Product> findByCategorys(Integer id);

    List<Product> findTop5All();

    List<Product> findTop5Men(Integer gender);

    List<Product> findTop5Discount();

    List<Product> findTop5Special();

    List<Product> findTop5New();

    void updateSeen(Product product);

    List<Product> findTop4Men(Integer gender);

    Page<Product> findById(Integer id,Pageable pageable);

    Page<Product> findByBestSeller(Pageable pageable);
}

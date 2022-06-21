package com.clothes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ProductDAO extends JpaRepository<Product,Integer> {


    @Query("SELECT count(p) FROM  Product p WHERE  p.pname=?1")
    Integer checkProductName(String name);

    Page<Product> findBySpecialIsTrueOrderByCreateDateDesc(Pageable pageable);

    @Query("SELECT o FROM Product o WHERE o.discount > 0  ORDER BY  o.createDate DESC ")
    Page<Product> findByDiscount(Pageable pageable);

    @Query("SELECT o FROM Product o WHERE o.pname like?1 OR o.category.cname like?1")
    Page<Product> findByKeywords(String keywords, Pageable pageable);

    @Query("SELECT o FROM Product o WHERE o.category.cid=?1")
    Page<Product> findByCategory(Integer id, Pageable pageable);

    @Query("SELECT o FROM Product o WHERE o.category.cid=?1")
    List<Product> findByCategorys(Integer id);

    @Query("SELECT DISTINCT o.product FROM OrderDetail o "
            + "WHERE o.order.recipient.id=?1")
    Page<Product> findByPid(Integer id,Pageable pageable);


    //select top 5 product discount order by createDate desc
    List<Product> findTop5ByDiscountGreaterThanOrderByCreateDateDesc(Double discount);

    // select top 5 product special order by createDate desc
    List<Product> findTop5BySpecialIsTrueOrderByCreateDateDesc();

     // select top 5 product order by createDate desc
    List<Product> findTop5ByOrderByCreateDateDesc();

    List<Product> findTop5ByGenderOrderByCreateDateDesc(Integer gender);

    List<Product> findTop4ByGenderOrderByCreateDateDesc(Integer gender);

    List<Product> findTop3ByOrderBySeenDesc();


    @Query("SELECT d.product.pid FROM OrderDetail d "
            + " GROUP BY d.product.pid "
            + " ORDER BY sum(d.price*d.quantity*(1-d.discount))")
    List<Integer> findByBestSellerIds(Pageable pageable);


    @Query("SELECT p FROM Product p WHERE p.pid IN ?1")
    Page<Product> findByIds(List<Integer> ids, Pageable pageable);
}

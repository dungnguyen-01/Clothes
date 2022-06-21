package com.clothes.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ReportDAO extends JpaRepository<Order, Long> {

    @Query("SELECT  o.pname AS group, o.seen AS count FROM Product o "
            + "WHERE o.seen > 0"
            + " ORDER BY o.seen DESC "
    )
    List<Report> getTopLikes(Pageable pageable);

    @Query("SELECT o.pname AS group, size(o.shares) AS count"
            + " FROM Product o "
            + " WHERE o.shares IS NOT EMPTY")
    List<Report> getTopShares(Pageable pageable);

    @Query("SELECT o.category.cname AS group,"
            + " SUM(o.quantity) AS count,"
            + " SUM(o.quantity * o.price) AS sum,"
            + " MIN(o.price) AS min, "
            + " MAX(o.price) AS max, "
            + " AVG(o.price) AS avg"
            + " FROM Product o "
            + " GROUP BY o.category.cname")
    List<Report> getInventoryByCategory();
}

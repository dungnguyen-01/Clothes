package com.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderDAO extends JpaRepository<Order,Long> {

    @Query("select o from Order o where o.recipient.id=?1")
    List<Order> findByRecipient(Integer id);

    List<Order> findByStatusSidOrderByCreateDateDesc(Integer id);
}

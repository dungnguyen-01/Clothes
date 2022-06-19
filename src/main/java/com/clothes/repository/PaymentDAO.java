package com.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDAO extends JpaRepository<Payment,Integer> {
}

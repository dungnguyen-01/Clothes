package com.clothes.repository;

import com.clothes.repository.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Shares")
public class Share {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    @Column(length = 70)
    String sender;
    @Column(length = 70)
    String receiver;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sentdate")
    Date sentDate = new Date();
    @Column(length = 120)
    String subject;
    String text;
    @Column(length = 20)
    String categorySend;

    @ManyToOne
    @JoinColumn(name="productid")
    Product product;
}
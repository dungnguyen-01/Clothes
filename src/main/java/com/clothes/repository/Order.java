package com.clothes.repository;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "o_id")
    private Long oid;

    @Column(length = 15)
    private String mobile;
    private String address;
    private Double amount;
    private String notes;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;

    @Column(name = "update_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    Date updateDate;

    @ManyToOne
    @JoinColumn(name = "recipient")
    Account recipient;

    @ManyToOne
    @JoinColumn(name = "handlername")
    Account handlerName;

    @ManyToOne
    @JoinColumn(name = "paymentid")
    Payment payment;

    @ManyToOne
    @JoinColumn(name = "statusid")
    Status status;

    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails;
}

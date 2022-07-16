package com.clothes.repository;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "OrderDetails")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "od_id")
    private Long odid;
    private Integer quantity;
    private Double price;
    private String size;
    private String color;
    private Double discount;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date createDate;

    @Column(name = "update_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date updateDate;

    @ManyToOne
    @JoinColumn(name = "orderid")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Order order;

    @ManyToOne
    @JoinColumn(name = "productid")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Product product;


    public OrderDetail(Integer quantity, String size, String color, Order order, Product product) {
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.order = order;
        this.createDate = new Date();
        this.product = product;
        this.discount = product.getDiscount();
        this.price = product.getPrice();

    }
}

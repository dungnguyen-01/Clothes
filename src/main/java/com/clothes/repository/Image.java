package com.clothes.repository;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name ="Images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "i_id")
    private int iid;
    @Column(name = "i_name" ,length =50 )
    private String iname;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date createDate = new Date();

    @Column(name = "update_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date updateDate = new Date();

    @ManyToOne
    @JoinColumn(name = "productid")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Product product;

    public Image(String iname, Product product) {
        this.iname = iname;
        this.product = product;
    }
}

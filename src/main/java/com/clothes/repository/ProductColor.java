package com.clothes.repository;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ProductColors")
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true)
    private Integer id;

    public  ProductColor (Product product, Color color){
        this.product=product;
        this.color =color;
    }

    @ManyToOne
    @JoinColumn(name = "colorid")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Color color;

    @ManyToOne
    @JoinColumn(name = "productid")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Product product;

}

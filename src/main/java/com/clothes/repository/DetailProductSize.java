package com.clothes.repository;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "DetailProductSize")
public class DetailProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true)
    private Integer id;

    public DetailProductSize(Product product, Size size) {
        this.product = product;
        this.size = size;
    }

    @ManyToOne
    @JoinColumn(name = "sizeid")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Size size;

    @ManyToOne
    @JoinColumn(name = "productid")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Product product;

}

package com.clothes.repository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Categories")

public class Category {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Integer cid;

    @NotBlank(message = "Không được để trống.")
    @Column(name = "c_name",length = 70,unique = true)
    private String cname;

    @OneToMany(mappedBy = "category")
    List<Product> products;

}

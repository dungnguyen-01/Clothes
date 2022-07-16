package com.clothes.repository;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_id",unique = true)
    private Integer sid;

    @NotBlank(message = "Vui lòng nhập đầy đủ, không được để trống.")
    @Column(name = "s_name",length = 20)
    private String sname;

    @Column(name = "type_of_item")
    private boolean typeOfItem;

    @OneToMany(mappedBy = "size",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<DetailProductSize> detailProductSizes;
}

package com.clothes.repository;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Colors")

public class Color {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Integer cid;

    @Column(name = "c_name")
    private String cname;

    @Column(name = "txtcolor")
    private String txtColor;

    @Column(name = "bgcolor")
    private String bgColor;

    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<ProductColor> productColors;

}
package com.clothes.repository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private Integer pid;

    @NotEmpty(message = "Vui lòng không để trống tên sản phẩm và trùng tên.")
    @Column(name = "p_name")
    private String pname;

    @Column(name="image_m")
    private String image = "new.png";

    @NotNull(message = "Vui lòng nhập giá")
    @Min(value = 10,message = "Giá lớn hơn 10.")
    @Max(value = 1000000,message = "Giá nhỏ hơn 1.000.000.")
    private double price;
    @NotNull(message = "Số lượng lớn hơn 1.")
    private int quantity = 1;
    @NotNull(message = "Vui lòng check vào bên dưới.")
    private Integer gender;
    private double discount;
    private String description;
    @NotNull(message = "Vui lòng check vào bên dưới.")
    private Boolean special;

    @NotNull(message = "Vui lòng check vào bên dưới.")
    private Boolean available;

    private int seen;
    @Column(name="add_name")
    private String addName ="Nguyen Van A";
    private Boolean status = false;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date createDate = new Date();

    @Column(name = "update_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date updateDate = new Date();

    @ManyToOne
    @JoinColumn(name = "categoryid")
    Category category;


    @OneToMany(mappedBy = "product")
    List<DetailProductSize> detailProductSizes;

    @OneToMany(mappedBy = "product")
    List<ProductColor> productColors;

    @OneToMany(mappedBy = "product")
    List<Image> images;

    @OneToMany(mappedBy = "product")
    List<Share> shares;
    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails;

    public boolean hasSize(Size size) {
        if(this.detailProductSizes != null) {
            return this.detailProductSizes.stream()
                    .anyMatch(a -> a.getSize().getSid().equals(size.getSid()));
        }
        return false;
    }

    public  boolean hasColor(Color color){
        if (this.productColors != null){
            return  this.productColors.stream().
                    anyMatch( c -> c.getColor().getCid().equals(color.getCid()));
        }
        return  false;
    }

    public double getPromotePrice() {
        return this.price * (1 - this.discount);
    }
}

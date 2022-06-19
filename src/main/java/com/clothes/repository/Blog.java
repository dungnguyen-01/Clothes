package com.clothes.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 80)

    @NotEmpty(message = "Vui lòng không để trống tên tiêu đề.")
    private String title;

    @Column(length = 40)
    private String author="Nguyen Van A";
    private int seen;
    @Column(length = 50)
    private String image = "new.png";
    private String descr;

    @Column(name = "create_date")
    String createDate;

    @Column(name = "update_date")
    String updateDate;

    public Blog(String title, String author, int seen, String image, String descr, String createDate) {
        this.title = title;
        this.author = author;
        this.seen = seen;
        this.image = image;
        this.descr = descr;
        this.createDate = createDate;
    }

    public Blog(int id,String title, String author, int seen, String image, String descr, String createDate, String updateDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.seen = seen;
        this.image = image;
        this.descr = descr;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}

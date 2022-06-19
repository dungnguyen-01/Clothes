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
@Table(name = "Statuses")
public class Status {
    @Id
    @Column(name="s_id")
    private Integer sid;
    @Column(name="s_name",length = 50)
    private String sname;
    private String skin;

    @OneToMany(mappedBy = "status")
    List<Order> orders;

}

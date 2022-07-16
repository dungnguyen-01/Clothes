package com.clothes.repository;


import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Vui lòng không được để trống.")
    @Column(name = "fullname", length = 50 )
    private String fullname;

    @NotEmpty(message = "Vui lòng không được để trống.")
    @Column(name = "password", length = 65 )
    private String password;

    @NotEmpty(message = "Vui lòng không được để trống.")
    @Email(message = "Không đúng định dạng email.")
    @Column(name = "email", length = 50)
    private String email;

    @NotEmpty(message = "Vui lòng không được để trống.")
    @Column(name = "mobile", length = 12)
    private String mobile;

    private boolean gender;
    private boolean activated = false;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date createDate = new Date();

    @Column(name = "update_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date updateDate = new Date();

    public Account(Integer id, String fullname, String password, String email, String mobile, boolean gender, boolean activated, Date createDate, Date updateDate, Role role) {
        this.id = id;
        this.fullname = fullname;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.activated = activated;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.role = role;
    }

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<Order> recipients;

    @OneToMany(mappedBy = "handlerName", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<Order> handlerNames;


    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<Contact> contacts;


    @ManyToOne(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "roleid")
    Role role;

    public boolean hasRole(Role role) {
        if (this.role != null) {
            return this.role.getRid() == role.getRid();
        }
        return false;
    }

}

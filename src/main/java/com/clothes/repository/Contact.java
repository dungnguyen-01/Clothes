package com.clothes.repository;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Entity
@Data
@Table(name ="Contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullname;
    private String email;
    private String message;
    private Boolean status;

    @Column(name = "senddate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;

    @Column(name ="processdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processDate;

    @ManyToOne
    @JoinColumn(name = "handler")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Account account;

    public Contact(String fullname, String email, String message, Boolean status, Date sendDate) {
        this.fullname = fullname;
        this.email = email;
        this.message = message;
        this.status = status;
        this.sendDate = sendDate;
    }

    public Contact(Integer id, String fullname, String email, String message, Boolean status, Date sendDate,Date processDate, Account account) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.message = message;
        this.status = status;
        this.sendDate = sendDate;
        this.processDate = processDate;
        this.account = account;
    }

}

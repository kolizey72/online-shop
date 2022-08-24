package com.github.kolizey72.onlineshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;

    @Column(name = "class")
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private UserClass userClass;

    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private UserProfession userProfession;

    @Temporal(TemporalType.DATE)
    @Getter @Setter
    private Date birthday;

    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date registration;

    @Getter @Setter
    private Boolean banned;

    @OneToMany(mappedBy = "seller")
    @Getter @Setter
    private List<Product> products;
}

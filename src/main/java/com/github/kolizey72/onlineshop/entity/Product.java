package com.github.kolizey72.onlineshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Double price;

    @Column(name = "class")
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private UserClass userClass;

    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private UserProfession userProfession;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Getter @Setter
    private User seller;
}

package com.github.kolizey72.onlineshop.entity;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "class")
    @Enumerated(EnumType.STRING)
    private UserClass userClass;

    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    private UserProfession userProfession;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User seller;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public UserClass getUserClass() {
        return userClass;
    }

    public void setUserClass(UserClass userClass) {
        this.userClass = userClass;
    }

    public UserProfession getUserProfession() {
        return userProfession;
    }

    public void setUserProfession(UserProfession userProfession) {
        this.userProfession = userProfession;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}

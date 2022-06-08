package com.netcracker.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="purchase")
@Data
@Getter
@Setter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "pur_data",nullable = false)
    private Date data;
    @ManyToOne
    @JoinColumn(name = "shop")
    private Shop shop;
    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "book")
    private Books book;

    private int numb;

    private double sum;

    public Purchase(int id, Date data, Shop shop, Customer customer, Books book, int numb, double sum) {
        this.id = id;
        this.data = data;
        this.shop = shop;
        this.customer = customer;
        this.book = book;
        this.numb = numb;
        this.sum = sum;
    }
    public Purchase(){
    }

}
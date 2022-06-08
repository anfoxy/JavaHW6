package com.netcracker.builder;

import com.netcracker.model.Books;
import com.netcracker.model.Customer;
import com.netcracker.model.Purchase;
import com.netcracker.model.Shop;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

public class PurchaseBuilder {
    private int id;

    private Date data;

    private Shop shop;

    private Customer customer;

    private Books book;

    private int numb;

    private double sum;


    public PurchaseBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public PurchaseBuilder setDate(Date data) {
        this.data = data;
        return this;
    }

    public PurchaseBuilder setShop(Shop shop) {
        this.shop = shop;
        return this;
    }

    public PurchaseBuilder setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public PurchaseBuilder setBooks(Books book) {
        this.book = book;
        return this;
    }

    public PurchaseBuilder setNumb(int numb) {
        this.numb = numb;
        return this;
    }
    public PurchaseBuilder setSum(double sum) {
        this.sum = sum;
        return this;
    }

    public Purchase createPurchase() {
        return new Purchase(id, data, shop, customer,book,numb,sum);
    }
}
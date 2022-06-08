package com.netcracker.dto;

import com.netcracker.model.Books;
import com.netcracker.model.Customer;
import com.netcracker.model.Purchase;
import com.netcracker.model.Shop;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
@Getter
public class PurchaseDto {

    private int id;
    private Date data;
    private Shop shop;
    private Customer customer;
    private Books book;
    private int numb;
    private double sum;

    public PurchaseDto(int id, Date data, Shop shop, Customer customer, Books book, int numb, double sum) {
        this.id = id;
        this.data = data;
        this.shop = shop;
        this.customer = customer;
        this.book = book;
        this.numb = numb;
        this.sum = sum;
    }

    public PurchaseDto(Purchase c) {
        this.id = c.getId();
        this.data = c.getData();
        this.shop = c.getShop();
        this.customer = c.getCustomer();
        this.book = c.getBook();
        this.numb = c.getNumb();
        this.sum = c.getSum();
    }

    public void setId(int id) {
        this.id = id;
    }

    public Purchase convertToEntity() {
        Purchase c = new Purchase();
        c.setId(id);
        c.setData(data);
        c.setShop(shop);
        c.setCustomer(customer);
        c.setBook(book);
        c.setNumb(numb);
        c.setSum(sum);

        return c;
    }


}


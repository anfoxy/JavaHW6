package com.netcracker.builder;

import com.netcracker.model.Books;
import com.netcracker.model.Customer;

public class CustomerBuilder {
    private int id;

    private String ln;

    private String area;

    private double discount;

    public CustomerBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CustomerBuilder setLn(String ln) {
        this.ln = ln;
        return this;
    }

    public CustomerBuilder setArea(String area) {
        this.area = area;
        return this;
    }

    public CustomerBuilder setDiscount(int discount) {
        this.discount = discount;
        return this;
    }


    public Customer createCustomer() {
        return new Customer(id, ln, area, discount);
    }
}
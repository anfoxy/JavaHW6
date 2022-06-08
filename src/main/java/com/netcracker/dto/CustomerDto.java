package com.netcracker.dto;

import com.netcracker.model.Books;
import com.netcracker.model.Customer;
import lombok.Getter;

@Getter
public class CustomerDto {

    private int id;

    private String ln;

    private String area;

    private double discount;

    public CustomerDto(int id, String ln, String area, double discount) {
        this.id = id;
        this.ln = ln;
        this.area = area;
        this.discount = discount;
    }

    public CustomerDto(Customer c) {
        this.id = c.getId();
        this.ln = c.getLn();
        this.area = c.getArea();
        this.discount = c.getDiscount();
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer convertToEntity() {
       Customer c = new Customer();
        c.setId(id);
        c.setLn(ln);
        c.setArea(area);
        c.setDiscount(discount);
       return c;
    }

}


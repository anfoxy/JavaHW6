package com.netcracker.dto;

import com.netcracker.model.Books;
import lombok.Getter;

@Getter
public class BookDto {

    private int id;
    private String name;
    private double cost;
    private String warehouse;
    private int numb;

    public BookDto(int id, String name, double cost, String warehouse, int numb) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.warehouse = warehouse;
        this.numb = numb;
    }

    public BookDto(Books c) {
        this.id = c.getId();
        this.name = c.getName();
        this.cost = c.getCost();
        this.warehouse = c.getWarehouse();
        this.numb = c.getNumb();
    }

    public long setId(int id) {
        return this.id = id;
    }


    public Books convertToEntity() {
        Books c = new Books();
        c.setId(id);
        c.setName(name);
        c.setCost(cost);
        c.setWarehouse(warehouse);
        c.setNumb(numb);
        return c;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", warehouse='" + warehouse + '\'' +
                ", numb=" + numb +
                '}';
    }
}


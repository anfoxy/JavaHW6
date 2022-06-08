package com.netcracker.builder;

import com.netcracker.model.Books;

public class BooksBuilder {
    private int id;
    private String name;
    private int cost;
    private String warehouse;
    private int numb;

    public BooksBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public BooksBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BooksBuilder setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public BooksBuilder setWarehouse(String warehouse) {
        this.warehouse = warehouse;
        return this;
    }

    public BooksBuilder setNumb(int numb) {
        this.numb = numb;
        return this;
    }

    public Books createBooks() {
        return new Books(id, name, cost, warehouse, numb);
    }
}
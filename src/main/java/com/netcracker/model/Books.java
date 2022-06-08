package com.netcracker.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="books")
@Data
@Getter
@Setter
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private double cost;
    private String warehouse;
    private int numb;

    public Books(int id, String name, double cost, String warehouse, int numb) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.warehouse = warehouse;
        this.numb = numb;
    }

    public Books() {
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", warehouse='" + warehouse + '\'' +
                ", numb=" + numb +
                '}';
    }

}

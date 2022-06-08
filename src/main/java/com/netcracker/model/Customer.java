package com.netcracker.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="customer")
@Data
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String ln;

    private String area;

    private double discount;

    public Customer(int id, String ln, String area, double discount) {
        this.id = id;
        this.ln = ln;
        this.area = area;
        this.discount = discount;
    }

    public Customer() {
    }
}

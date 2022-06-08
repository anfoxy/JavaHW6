package com.netcracker.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="shop")
@Data
@Getter
@Setter
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String area;

    private int commission;

    public Shop(int id, String name, String area, int commission) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.commission = commission;
    }
    public Shop() {
    }

}

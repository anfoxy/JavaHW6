package com.netcracker.dto;

import com.netcracker.model.Customer;
import com.netcracker.model.Shop;
import lombok.Getter;

@Getter
public class ShopDto {

    private int id;

    private String name;

    private String area;

    private int commission;

    public ShopDto(int id, String name, String area, int commission) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.commission = commission;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ShopDto(Shop c) {
        this.id = c.getId();
        this.name = c.getName();
        this.area = c.getArea();
        this.commission = c.getCommission();
    }

   public Shop convertToEntity() {
       Shop c = new Shop();
        c.setId(id);
        c.setName(name);
        c.setArea(area);
        c.setCommission(commission);
        return c;
    }

}


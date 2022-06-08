package com.netcracker.builder;

import com.netcracker.model.Customer;
import com.netcracker.model.Shop;

public class ShopBuilder {
    private int id;

    private String name;

    private String area;

    private int commission;


    public ShopBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ShopBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ShopBuilder setArea(String area) {
        this.area = area;
        return this;
    }

    public ShopBuilder setCommission(int commission) {
        this.commission = commission;
        return this;
    }


    public Shop createShop() {
        return new Shop(id, name, area, commission);
    }
}
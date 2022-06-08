package com.netcracker.service;

import com.netcracker.dto.ShopDto;
import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.mapper.CustomerMapper;
import com.netcracker.model.Shop;
import com.netcracker.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShopService {

    @Autowired
    ShopRepository repo;
    @Autowired
    CustomerMapper mapper;

    public Shop putMet(Integer id, Shop req) throws ResourceNotFoundException {
        Shop shop = findById(id);
        shop.setName(req.getName());
        shop.setArea(req.getArea());
        shop.setCommission(req.getCommission());
        repo.save(shop);
        return shop;
    }

    public Shop updateCustomer(Shop d, int id) throws ResourceNotFoundException {
        ShopDto dto = new ShopDto(d);
        dto.setId(id);
        Shop shop = findById(id);
        mapper.updateShopFromDto(dto, shop);
        repo.save(shop);
        return shop;
    }

    public void delete(int id) throws ResourceNotFoundException {
        Shop shop = findById(id);
        repo.delete(shop);
    }
    public Shop save(Shop shop){
        return repo.save(shop);
    }
    public Shop findById(int id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Shop not found for id:" + id + ""));
    }
    public List<Shop> findAll() {
        return repo.findAll();
    }

    public List<String> searchStoreNamesForTwoArea(String area1, String area2) {
        List<String> list = repo.searchStoreNamesForTwoArea(area1,area2);
        if(list.isEmpty()) list.add("nothing was found for your query");
        return list;
    }
}

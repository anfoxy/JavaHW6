package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Shop;
import com.netcracker.respons.DeleteResponse;
import com.netcracker.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class ShopController {
    @Autowired
    ShopService shopService;

    @GetMapping("/shop/search_store_names_for_two_area/{area1}/{area2}")
    public List<String> searchStoreNamesForTwoArea(@PathVariable String area1, @PathVariable String area2) {
        return shopService.searchStoreNamesForTwoArea(area1,area2);
    }
    @GetMapping("/shop")
    public List<Shop> getAllShop() {
        return shopService.findAll();
    }


    @GetMapping("/shop/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(shopService.findById(id));
    }

    @PostMapping("/shop")
    public Shop createShop(@RequestBody Shop shop){
        return  shopService.save(shop);
    }

    @DeleteMapping("/shop/{id}")
    public ResponseEntity<DeleteResponse> deleteShop(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        shopService.delete(id);
        return ResponseEntity.ok(new DeleteResponse("Shop with id:"+id+" deleted"));
    }

    @PatchMapping(value = "/shop/{id}")
    public ResponseEntity<Shop> patchShop(@PathVariable Integer id, @RequestBody Shop data) throws ResourceNotFoundException {
        return ResponseEntity.ok(shopService.updateCustomer(data,id));
    }

    @PutMapping("/shop/{id}")
    public ResponseEntity<Shop> putShop(@PathVariable Integer id,@RequestBody Shop req) throws ResourceNotFoundException {
        return ResponseEntity.ok(shopService.putMet(id,req));
    }
}

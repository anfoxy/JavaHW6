package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Purchase;
import com.netcracker.respons.DeleteResponse;
import com.netcracker.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/rest")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;



    @GetMapping("/purchase/distinct_all_date")
    public List<String> distinctAllDate() {
        return purchaseService.distinctAllDate();
    }

    @GetMapping("/purchase/all_customer_and_shop")
    public List<String> getAllCustomerAndShop() {
        return purchaseService.getAllCustomerAndShop();
    }

    @GetMapping("/purchase/all_info")
    public List<String> getAllInfo() {
        return purchaseService.getAllInfo();
    }

    @GetMapping("/purchase/sum/{sum}")
    public List<String> getPurchaseWhereSumGreaterSpecified(@PathVariable double sum) {
        return purchaseService.getPurchaseWhereSumGreaterSpecified(sum);
    }

    @GetMapping("/purchase/month_and_area/{data}")
    public List<String> searchByDateAndTheSameArea(@PathVariable int data) {
        return purchaseService.searchByDateAndTheSameArea(data);
    }

    @GetMapping("/purchase/not_area/{minDiscount}/{maxDiscount}/{area}")
    public List<String> purchaseWithCertainDiscountRangeWhoBoughtElsewhere( @PathVariable int minDiscount,
                                       @PathVariable int maxDiscount,
                                       @PathVariable String area)
    {return purchaseService.purchaseWithCertainDiscountRangeWhoBoughtElsewhere(minDiscount,maxDiscount,area);}

    @GetMapping("/purchase/area_and_numb")
    public List<String> searchByAreaAndNumb() {
        return purchaseService.searchByAreaAndNumb();
    }

    @GetMapping("/purchase")
    public List<Purchase> getAllPurchase() {
        return purchaseService.findAll();
    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(purchaseService.findById(id));}

    @PostMapping("/purchase")
    public Purchase createPurchase(@RequestBody Purchase purchase){
        return  purchaseService.save(purchase);
    }

    @DeleteMapping("/purchase/{id}")
    public ResponseEntity<DeleteResponse> deletePurchase(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        purchaseService.delete(id);
        return ResponseEntity.ok(new DeleteResponse("Purchase with id:"+id+" deleted"));
    }

    @PatchMapping(value = "/purchase/{id}")
    public ResponseEntity<Purchase> patchPurchase(@PathVariable Integer id, @RequestBody Purchase data) throws ResourceNotFoundException {
        return ResponseEntity.ok(purchaseService.updateCustomer(data,id));
    }

    @PutMapping("/purchase/{id}")
    public ResponseEntity<Purchase> putPurchase(@PathVariable Integer id,@RequestBody Purchase req) throws ResourceNotFoundException {
        return ResponseEntity.ok(purchaseService.putMet(id,req));
    }


}

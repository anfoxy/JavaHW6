package com.netcracker.service;

import com.netcracker.dto.PurchaseDto;
import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.mapper.CustomerMapper;
import com.netcracker.model.Purchase;

import com.netcracker.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PurchaseService {

    @Autowired
    PurchaseRepository repo;
    @Autowired
    CustomerMapper mapper;

    public Purchase putMet(Integer id,Purchase req) throws ResourceNotFoundException {
        Purchase purchase = findById(id);
        purchase.setData(req.getData());
        purchase.setShop(req.getShop());
        purchase.setCustomer(req.getCustomer());
        purchase.setBook(req.getBook());
        purchase.setNumb(req.getNumb());
        purchase.setSum(req.getSum());
        repo.save(purchase);
        return  purchase;
    }

    public Purchase updateCustomer(Purchase d, int id) throws ResourceNotFoundException {
        PurchaseDto dto = new PurchaseDto(d);
        dto.setId(id);
        Purchase purchase = findById(id);
        System.out.println(purchase);
        System.out.println(dto);
        mapper.updatePurchaseFromDto(dto, purchase);
        repo.save(purchase);
        return purchase;
    }

    public void delete(int id) throws ResourceNotFoundException {
        Purchase purchase = findById(id);
        repo.delete(purchase);
    }
    public Purchase save(Purchase purchase){
        return repo.save(purchase);
    }
    public Purchase findById(int id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Purchase not found for id:" + id + ""));
    }
    public List<Purchase> findAll() {
        return repo.findAll();
    }

    public List<String> searchByAreaAndNumb() {
        List<String> list = repo.searchByAreaAndNumb();
        return checkList(list);
    }

    public List<String> getAllInfo() {
        List<String> list = repo.getAllInfo();
        return checkList(list);
    }
    public List<String> getAllCustomerAndShop() {
        List<String> list = repo.getAllCustomerAndShop();
        return checkList(list);
    }

    public List<String> distinctAllDate() {
        List<String> list = repo.distinctAllDate();
        return checkList(list);
    }

    public List<String> purchaseWithCertainDiscountRangeWhoBoughtElsewhere(int minDiscount, int maxDiscount, String area) {
        List<String> list = repo.purchaseWithCertainDiscountRangeWhoBoughtElsewhere(minDiscount,maxDiscount,area);
        return checkList(list);
    }

    public List<String> searchByDateAndTheSameArea(int data) {
        List<String> list = repo.searchByDateAndTheSameArea(data);
        return checkList(list);
    }
    public List<String> getPurchaseWhereSumGreaterSpecified(double sum) {
        List<String> list = repo.getPurchaseWhereSumGreaterSpecified(sum);
        return checkList(list);
    }
    private List<String> checkList(List<String> list){
        if(list.isEmpty()) list.add("nothing was found for your query");
        return list;
    }

}

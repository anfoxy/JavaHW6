package com.netcracker.service;

import com.netcracker.dto.CustomerDto;
import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.mapper.CustomerMapper;
import com.netcracker.model.Customer;
import com.netcracker.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerService {

    @Autowired
    CustomerRepository repo;
    @Autowired
    CustomerMapper mapper;

    public Customer putMet(Integer id, Customer req) throws ResourceNotFoundException {
        Customer customer = findById(id);
        customer.setLn(req.getLn());
        customer.setArea(req.getArea());
        customer.setDiscount(req.getDiscount());
        repo.save(customer);
        return customer;
    }

    public Customer updateCustomer(Customer d, int id) throws ResourceNotFoundException {
        CustomerDto dto = new CustomerDto(d);
        dto.setId(id);
        Customer customer = findById(id);
        mapper.updateCustomerFromDto(dto, customer);
        repo.save(customer);
        return customer;
    }

    public void delete(int id) throws ResourceNotFoundException {
        Customer customer = findById(id);
        repo.delete(customer);
    }
    public Customer save(Customer customer){
        return repo.save(customer);
    }
    public Customer findById(int id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer not found for id:" + id + ""));
    }
    public List<Customer> findAll() {
        return repo.findAll();
    }

    public List<String> searchByArea(String area) {
        List<String> list = repo.searchByArea(area);
        if(list.isEmpty()) list.add("nothing was found for your query");
        return list;
    }
    public List<String> distinctArea() {
        List<String> list = repo.getDistinctNameCost();
        if(list.isEmpty()) list.add("nothing was found for your query");
        return list;
    }
}

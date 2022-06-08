package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Customer;
import com.netcracker.respons.DeleteResponse;
import com.netcracker.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class CustomerController {
    @Autowired
    CustomerService customerService;


    @GetMapping("/customer/distinct_name_and_cost")
    public List<String> getDistinctNameCost() {
        return customerService.distinctArea();
    }

    @GetMapping("/customer/search_by_area/{area}")
    public List<String> searchByArea(@PathVariable(value = "area") String area) {
        return customerService.searchByArea(area);
    }

    @GetMapping("/customer")
    public List<Customer> getAllCustomer() {
        return customerService.findAll();
    }


    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PostMapping("/customer")
    public Customer createCustomer(@RequestBody Customer customer){
        return  customerService.save(customer);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<DeleteResponse> deleteCustomer(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        customerService.delete(id);
        return ResponseEntity.ok(new DeleteResponse("Customer with id:"+id+" deleted"));
    }

    @PatchMapping(value = "/customer/{id}")
    public ResponseEntity<Customer> patchCustomer(@PathVariable Integer id, @RequestBody Customer customer) throws ResourceNotFoundException {
        return ResponseEntity.ok(customerService.updateCustomer(customer,id));
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> putCustomer(@PathVariable Integer id,@RequestBody Customer req) throws ResourceNotFoundException {
        return ResponseEntity.ok(customerService.putMet(id,req));
    }
}

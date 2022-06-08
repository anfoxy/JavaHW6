package com.netcracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.builder.CustomerBuilder;
import com.netcracker.model.Books;
import com.netcracker.model.Customer;
import com.netcracker.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerBuilder customerBuilder;
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void after() throws Exception{
        customerRepository.deleteAll();
    }

    @Test
    void testGetAllCustomer() throws Exception {
        Customer customer1 = customerBuilder.setLn("Иванов").setArea("Сормово").setDiscount(20).createCustomer();
        customerRepository.save(customer1);

        Customer customer2 = customerBuilder.setLn("Крылова").setArea("Автозавод").setDiscount(10).createCustomer();
        customerRepository.save(customer2);

        mockMvc.perform(get("/rest/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ln").value("Иванов"))
                .andExpect(jsonPath("$[0].area").value("Сормово"))
                .andExpect(jsonPath("$[0].discount").value(20))
                .andExpect(jsonPath("$[1].ln").value("Крылова"))
                .andExpect(jsonPath("$[1].area").value("Автозавод"))
                .andExpect(jsonPath("$[1].discount").value(10));
    }

    @Test
    void testGetDistinctNameCost() throws Exception{
        Customer customer1 = customerBuilder.setLn("Крылова").setArea("Нижний").setDiscount(10).createCustomer();
        customerRepository.save(customer1);
        Customer customer2 = customerBuilder.setLn("Иванов").setArea("Сормово").setDiscount(20).createCustomer();
        customerRepository.save(customer2);

        mockMvc.perform(get("/rest/customer/distinct_name_and_cost"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Нижний"))
                .andExpect(jsonPath("$[1]").value("Сормово"));

    }

    @Test
    void testSearchByArea() throws Exception{
        Customer customer1 = customerBuilder.setLn("Крылова").setArea("Нижний").setDiscount(10).createCustomer();
        customerRepository.save(customer1);
        Customer customer2 = customerBuilder.setLn("Иванов").setArea("Сормово").setDiscount(20).createCustomer();
        customerRepository.save(customer2);

        mockMvc.perform(get("/rest/customer/search_by_area/{area}", "Нижний"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Крылова,10"));
    }

    @Test
    void testGetCustomerById() throws Exception {

        Customer customer1 = customerBuilder.setLn("Иванов").setArea("Сормово").setDiscount(20).createCustomer();
        customerRepository.save(customer1);

        mockMvc.perform(get("/rest/customer/{id}", customer1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ln").value("Иванов"))
                .andExpect(jsonPath("$.area").value("Сормово"))
                .andExpect(jsonPath("$.discount").value(20));
    }

    @Test
    void testCreateCustomer()throws Exception {
        Customer customer1 = customerBuilder.setLn("Иванов").setArea("Сормово").setDiscount(20).createCustomer();

        mockMvc.perform(post("/rest/customer")
                        .content(objectMapper.writeValueAsString(customer1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ln").value("Иванов"))
                .andExpect(jsonPath("$.area").value("Сормово"))
                .andExpect(jsonPath("$.discount").value(20));
    }

    @Test
    void testDeleteCustomer() throws Exception{
        Customer customer1 = customerBuilder.setLn("Иванов").setArea("Сормово").setDiscount(20).createCustomer();
        customerRepository.save(customer1);

        mockMvc.perform(delete("/rest/customer/{id}", customer1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Customer with id:"+customer1.getId()+" deleted"));

    }

    @Test
    void testPatchCustomer() throws Exception{

        Customer customer1 = customerBuilder.setLn("Иванов").setArea("Сормово").setDiscount(20).createCustomer();
        customerRepository.save(customer1);

        mockMvc.perform(patch("/rest/customer/{id}", customer1.getId())
                        .content(objectMapper.writeValueAsString(customer1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ln").value("Иванов"))
                .andExpect(jsonPath("$.area").value("Сормово"))
                .andExpect(jsonPath("$.discount").value(20));
    }

    @Test
    void testPutCustomer() throws Exception {

        Customer customer1 = customerBuilder.setLn("Иванов").setArea("Сормово").setDiscount(20).createCustomer();
        customerRepository.save(customer1);

        mockMvc.perform(put("/rest/customer/{id}", customer1.getId())
                        .content(objectMapper.writeValueAsString(customer1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ln").value("Иванов"))
                .andExpect(jsonPath("$.area").value("Сормово"))
                .andExpect(jsonPath("$.discount").value(20));
    }

}
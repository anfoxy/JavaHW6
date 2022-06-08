package com.netcracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.builder.BooksBuilder;
import com.netcracker.builder.CustomerBuilder;
import com.netcracker.builder.PurchaseBuilder;
import com.netcracker.builder.ShopBuilder;
import com.netcracker.model.Books;
import com.netcracker.model.Customer;
import com.netcracker.model.Purchase;
import com.netcracker.model.Shop;
import com.netcracker.repository.BookRepository;
import com.netcracker.repository.CustomerRepository;
import com.netcracker.repository.PurchaseRepository;
import com.netcracker.repository.ShopRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PurchaseControllerTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PurchaseBuilder purchaseBuilder;

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopBuilder shopBuilder;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerBuilder customerBuilder;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BooksBuilder booksBuilder;

    @AfterEach
    void after(){
        purchaseRepository.deleteAll();
        bookRepository.deleteAll();
        customerRepository.deleteAll();
        shopRepository.deleteAll();

    }
    @Test
    void testGetAllPurchase() throws Exception {

        Purchase purchase1 = createPurchase1();
        purchaseRepository.save(purchase1);
        Purchase purchase2 = createPurchase2();
        purchaseRepository.save(purchase2);

        mockMvc.perform(get("/rest/purchase"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].data").value("2020-05-02T21:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].shop.name").value("Первый"))
                .andExpect(jsonPath("$[0].customer.ln").value("Иванов"))
                .andExpect(jsonPath("$[0].book.name").value("Z Wind"))
                .andExpect(jsonPath("$[0].numb").value(20))
                .andExpect(jsonPath("$[0].sum").value(2020))
                .andExpect(jsonPath("$[1].data").value("2020-02-02T21:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].shop.name").value("Второй"))
                .andExpect(jsonPath("$[1].customer.ln").value("Сергеев"))
                .andExpect(jsonPath("$[1].book.name").value("Z Windows 1"))
                .andExpect(jsonPath("$[1].numb").value(20))
                .andExpect(jsonPath("$[1].sum").value(2020));
    }

    @Test
    void testGetDistinctNameCost() throws Exception {

        Purchase purchase1 = createPurchase1();
        purchaseRepository.save(purchase1);
        Purchase purchase2 = createPurchase2();
        purchaseRepository.save(purchase2);

        mockMvc.perform(get("/rest/purchase/distinct_all_date"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(2))
                .andExpect(jsonPath("$[1]").value(5));
    }

    @Test
    void testGetAllCustomerAndShop()throws Exception  {
        Purchase purchase1 = createPurchase1();
        purchaseRepository.save(purchase1);
        Purchase purchase2 = createPurchase2();
        purchaseRepository.save(purchase2);

        mockMvc.perform(get("/rest/purchase/all_customer_and_shop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Иванов,Первый"))
                .andExpect(jsonPath("$[1]").value("Сергеев,Второй"));
    }

    @Test
    void testGetAllInfo() throws Exception {
        Purchase purchase1 = createPurchase1();
        purchaseRepository.save(purchase1);
        Purchase purchase2 = createPurchase2();
        purchaseRepository.save(purchase2);

        mockMvc.perform(get("/rest/purchase/all_info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("2020-05-03,Иванов,20,Z Wind,20"))
                .andExpect(jsonPath("$[1]").value("2020-02-03,Сергеев,20,Z Windows 1,20"));
    }

    @Test
    void testSearchSum() throws Exception {
        Purchase purchase1 = createPurchase1();
        purchaseRepository.save(purchase1);
        Purchase purchase2 = createPurchase2();
        purchaseRepository.save(purchase2);

        mockMvc.perform(get("/rest/purchase/sum/{sum}", 2000))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(""+purchase1.getId()+",Иванов,2020-05-03"))
                .andExpect(jsonPath("$[1]").value(""+purchase2.getId()+",Сергеев,2020-02-03"));
    }

    @Test
    void testGetMonthAndArea() throws Exception {

        Purchase purchase1 = createPurchase1();
        purchaseRepository.save(purchase1);
        Purchase purchase2 = createPurchase2();
        purchaseRepository.save(purchase2);
        Purchase purchase3 = createPurchase3();
        purchaseRepository.save(purchase3);
        mockMvc.perform(get("/rest/purchase/month_and_area/{data}", 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Иванов,Сормово,2021-01-03"));
    }

    @Test
    void testSearchNotArea() throws Exception {
        Purchase purchase1 = createPurchase1();
        purchaseRepository.save(purchase1);
        Purchase purchase2 = createPurchase2();
        purchaseRepository.save(purchase2);
        Purchase purchase3 = createPurchase3();
        purchaseRepository.save(purchase3);

        mockMvc.perform(get("/rest/purchase/not_area/{minDiscount}/{maxDiscount}/{area}", 9,16,"Автозавод"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Первый,11,Сормово"));
    }

    @Test
    void testSearchAreaAndNumb() throws Exception {
        Purchase purchase1 = createPurchase1();
        purchaseRepository.save(purchase1);
        Purchase purchase2 = createPurchase2();
        purchaseRepository.save(purchase2);

        mockMvc.perform(get("/rest/purchase/area_and_numb"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Z Wind,Сормово,11,2020"));
    }

    @Test
    void testGetPurchaseById() throws Exception {

        Purchase purchase1 = createPurchase1();
        purchaseRepository.save(purchase1);

        mockMvc.perform(get("/rest/purchase/{id}", purchase1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("2020-05-02T21:00:00.000+00:00"))
                .andExpect(jsonPath("$.shop.name").value("Первый"))
                .andExpect(jsonPath("$.customer.ln").value("Иванов"))
                .andExpect(jsonPath("$.book.name").value("Z Wind"))
                .andExpect(jsonPath("$.numb").value(20))
                .andExpect(jsonPath("$.sum").value(2020));
    }

    @Test
    void testCreatePurchase() throws Exception {
        Purchase purchase1 = createPurchase1();

        mockMvc.perform(post("/rest/purchase")
                        .content(objectMapper.writeValueAsString(purchase1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("2020-05-02T21:00:00.000+00:00"))
                .andExpect(jsonPath("$.shop.name").value("Первый"))
                .andExpect(jsonPath("$.customer.ln").value("Иванов"))
                .andExpect(jsonPath("$.book.name").value("Z Wind"))
                .andExpect(jsonPath("$.numb").value(20))
                .andExpect(jsonPath("$.sum").value(2020));
    }

    @Test
    void testDeletePurchase() throws Exception {

        Purchase purchase1 = createPurchase1();
        purchaseRepository.save(purchase1);

        mockMvc.perform(delete("/rest/purchase/{id}", purchase1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Purchase with id:"+purchase1.getId()+" deleted"));
    }

    @Test
    void testPatchPurchase() throws Exception {
        Purchase purchase1 = createPurchase1();
        purchaseRepository.save(purchase1);

        mockMvc.perform(patch("/rest/purchase/{id}", purchase1.getId())
                        .content(objectMapper.writeValueAsString(purchase1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("2020-05-02T21:00:00.000+00:00"))
                .andExpect(jsonPath("$.shop.name").value("Первый"))
                .andExpect(jsonPath("$.customer.ln").value("Иванов"))
                .andExpect(jsonPath("$.book.name").value("Z Wind"))
                .andExpect(jsonPath("$.numb").value(20))
                .andExpect(jsonPath("$.sum").value(2020));
    }

    @Test
    void testPutPurchase() throws Exception {
        Purchase purchase1 = createPurchase1();
        purchaseRepository.save(purchase1);

        mockMvc.perform(put("/rest/purchase/{id}", purchase1.getId())
                        .content(objectMapper.writeValueAsString(purchase1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("2020-05-02T21:00:00.000+00:00"))
                .andExpect(jsonPath("$.shop.name").value("Первый"))
                .andExpect(jsonPath("$.customer.ln").value("Иванов"))
                .andExpect(jsonPath("$.book.name").value("Z Wind"))
                .andExpect(jsonPath("$.numb").value(20))
                .andExpect(jsonPath("$.sum").value(2020));

    }


    private Purchase createPurchase1(){
        Shop shop1 = shopBuilder
                .setName("Первый")
                .setArea("Сормово")
                .setCommission(20)
                .createShop();
        shopRepository.save(shop1);

        Books books1 = booksBuilder
                .setName("Z Wind")
                .setCost(200)
                .setNumb(11)
                .setWarehouse("Сормово")
                .createBooks();
        bookRepository.save(books1);

        Customer customer1 = customerBuilder.setLn("Иванов").setArea("Сормово").setDiscount(20).createCustomer();
        customerRepository.save(customer1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Purchase purchase1 = null;
        try {
            purchase1 = purchaseBuilder
                    .setShop(shop1)
                    .setDate(formatter.parse("2020-05-03"))
                    .setBooks(books1)
                    .setCustomer(customer1)
                    .setSum(2020)
                    .setNumb(20)
                    .createPurchase();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return purchase1;
    }

    private Purchase createPurchase2(){
        Shop shop1 = shopBuilder
                .setName("Второй")
                .setArea("Автозавод")
                .setCommission(20)
                .createShop();
        shopRepository.save(shop1);

        Books books1 = booksBuilder
                .setName("Z Windows 1")
                .setCost(200)
                .setNumb(11)
                .setWarehouse("Сормово")
                .createBooks();
        bookRepository.save(books1);

        Customer customer1 = customerBuilder.setLn("Сергеев").setArea("Сормово").setDiscount(20).createCustomer();
        customerRepository.save(customer1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Purchase purchase1 = null;
        try {
            purchase1 = purchaseBuilder
                    .setShop(shop1)
                    .setDate(formatter.parse("2020-02-03"))
                    .setBooks(books1)
                    .setCustomer(customer1)
                    .setSum(2020)
                    .setNumb(20)
                    .createPurchase();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return purchase1;
    }

    private Purchase createPurchase3(){
        Shop shop1 = shopBuilder
                .setName("Первый")
                .setArea("Сормово")
                .setCommission(20)
                .createShop();
        shopRepository.save(shop1);

        Books books1 = booksBuilder
                .setName("Z Wind")
                .setCost(200)
                .setNumb(11)
                .setWarehouse("Сормово")
                .createBooks();
        bookRepository.save(books1);

        Customer customer1 = customerBuilder.setLn("Иванов").setArea("Сормово").setDiscount(11).createCustomer();
        customerRepository.save(customer1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Purchase purchase1 = null;
        try {
            purchase1 = purchaseBuilder
                    .setShop(shop1)
                    .setDate(formatter.parse("2021-01-03"))
                    .setBooks(books1)
                    .setCustomer(customer1)
                    .setSum(2020)
                    .setNumb(20)
                    .createPurchase();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return purchase1;
    }
}
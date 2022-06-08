package com.netcracker.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.builder.ShopBuilder;
import com.netcracker.model.Shop;
import com.netcracker.repository.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class ShopControllerTest {

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopBuilder shopBuilder;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void after() throws Exception{
        shopRepository.deleteAll();
    }

    @Test
    void testGetAllShop() throws Exception {
        Shop shop1 = shopBuilder.setName("Первый").setArea("Сормово").setCommission(20).createShop();
        shopRepository.save(shop1);

        Shop shop2 = shopBuilder.setName("Второй").setArea("Советский").setCommission(200).createShop();
        shopRepository.save(shop2);

        mockMvc.perform(get("/rest/shop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Первый"))
                .andExpect(jsonPath("$[0].area").value("Сормово"))
                .andExpect(jsonPath("$[0].commission").value(20))
                .andExpect(jsonPath("$[1].name").value("Второй"))
                .andExpect(jsonPath("$[1].area").value("Советский"))
                .andExpect(jsonPath("$[1].commission").value(200));
    }

    @Test
    void testSearchStoreNamesForTwoArea()throws Exception {
        Shop shop1 = shopBuilder.setName("Первый").setArea("Сормово").setCommission(20).createShop();
        shopRepository.save(shop1);

        Shop shop2 = shopBuilder.setName("Второй").setArea("Советский").setCommission(200).createShop();
        shopRepository.save(shop2);

        Shop shop3 = shopBuilder.setName("Третий").setArea("Автозавод").setCommission(100).createShop();
        shopRepository.save(shop3);

        mockMvc.perform(get("/rest/shop/search_store_names_for_two_area/{area1}/{area2}",
                        "Сормово", "Советский"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Первый"))
                .andExpect(jsonPath("$[1]").value("Второй"));
    }

    @Test
    void testGetShopById()throws Exception {
        Shop shop1 = shopBuilder.setName("Первый").setArea("Сормово").setCommission(20).createShop();
        shopRepository.save(shop1);

        mockMvc.perform(get("/rest/shop/{id}", shop1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Первый"))
                .andExpect(jsonPath("$.area").value("Сормово"))
                .andExpect(jsonPath("$.commission").value(20));

    }

    @Test
    void testCreateShop()throws Exception {
        Shop shop1 = shopBuilder.setName("Первый").setArea("Сормово").setCommission(20).createShop();

        mockMvc.perform(post("/rest/shop")
                .content(objectMapper.writeValueAsString(shop1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Первый"))
                .andExpect(jsonPath("$.area").value("Сормово"))
                .andExpect(jsonPath("$.commission").value(20));

    }

    @Test
    void testDeleteShop()throws Exception {
        Shop shop1 = shopBuilder.setName("Первый").setArea("Сормово").setCommission(20).createShop();
        shopRepository.save(shop1);

        mockMvc.perform(delete("/rest/shop/{id}", shop1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Shop with id:"+shop1.getId()+" deleted"));
    }

    @Test
    void testPatchShop()throws Exception {
        Shop shop1 = shopBuilder.setName("Первый").setArea("Сормово").setCommission(20).createShop();
        shopRepository.save(shop1);

        mockMvc.perform(patch("/rest/shop/{id}", shop1.getId())
                        .content(objectMapper.writeValueAsString(shop1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Первый"))
                .andExpect(jsonPath("$.area").value("Сормово"))
                .andExpect(jsonPath("$.commission").value(20));
    }

    @Test
    void testPutShop()throws Exception {
        Shop shop1 = shopBuilder.setName("Первый").setArea("Сормово").setCommission(20).createShop();
        shopRepository.save(shop1);

        mockMvc.perform(put("/rest/shop/{id}", shop1.getId())
                        .content(objectMapper.writeValueAsString(shop1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Первый"))
                .andExpect(jsonPath("$.area").value("Сормово"))
                .andExpect(jsonPath("$.commission").value(20));
    }
}
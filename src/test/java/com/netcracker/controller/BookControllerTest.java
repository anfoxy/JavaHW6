package com.netcracker.controller;

import com.netcracker.model.Books;
import com.netcracker.builder.BooksBuilder;
import com.netcracker.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BooksBuilder booksBuilder;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @AfterEach
    void after() {
        bookRepository.deleteAll();
    }

    @Test
    void testGetAllBook() throws Exception {

        Books books1 = booksBuilder
                .setName("Z Windows 1")
                .setCost(200)
                .setNumb(11)
                .setWarehouse("Сормово")
                .createBooks();
        bookRepository.save(books1);
        Books books2 = booksBuilder
                .setName("первый")
                .setCost(2000)
                .setNumb(23)
                .setWarehouse("Автозаводский")
                .createBooks();
        bookRepository.save(books2);
        mockMvc.perform(get("/rest/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Z Windows 1"))
                .andExpect(jsonPath("$[0].cost").value(200))
                .andExpect(jsonPath("$[0].numb").value(11))
                .andExpect(jsonPath("$[0].warehouse").value("Сормово"))
                .andExpect(jsonPath("$[1].name").value("первый"))
                .andExpect(jsonPath("$[1].cost").value(2000))
                .andExpect(jsonPath("$[1].numb").value(23))
                .andExpect(jsonPath("$[1].warehouse").value("Автозаводский"));
    }


    @Test
    void testDistinctAllNameAndCost() throws Exception {
        Books books1 = booksBuilder
                .setName("Z Windows 1")
                .setCost(200)
                .setNumb(11)
                .setWarehouse("Сормово")
                .createBooks();
        bookRepository.save(books1);
        Books books2 = booksBuilder
                .setName("первый")
                .setCost(2000)
                .setNumb(23)
                .setWarehouse("Автозаводский")
                .createBooks();
        bookRepository.save(books2);
        mockMvc.perform(get("/rest/book/all_name_and_cost"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Z Windows 1,200"))
                .andExpect(jsonPath("$[1]").value("первый,2000"));
    }

    @Test
    void testSearchForWordInNameBook() throws Exception {
        Books books1 = booksBuilder
                .setName("Z Windows 1")
                .setCost(200)
                .setNumb(11)
                .setWarehouse("Сормово")
                .createBooks();
        bookRepository.save(books1);
        Books books2 = booksBuilder
                .setName("первый")
                .setCost(2000)
                .setNumb(23)
                .setWarehouse("Автозаводский")
                .createBooks();
        bookRepository.save(books2);
        mockMvc.perform(get("/rest/book/word/{name}/{sum}", "Windows",1999))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Z Windows 1,200"))
                .andExpect(jsonPath("$[1]").value("первый,2000"));
    }

    @Test
    void testGetBookById() throws Exception {
        Books books1 = booksBuilder
                .setName("Z Windows 1")
                .setCost(200)
                .setNumb(11)
                .setWarehouse("Сормово")
                .createBooks();
        bookRepository.save(books1);

        mockMvc.perform(get("/rest/book/{id}", books1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Z Windows 1"))
                .andExpect(jsonPath("$.cost").value(200))
                .andExpect(jsonPath("$.numb").value(11))
                .andExpect(jsonPath("$.warehouse").value("Сормово"));
    }

    @Test
    void testCreateBooks() throws Exception {
        Books books1 = booksBuilder
                .setName("Z Windows 1")
                .setCost(200)
                .setNumb(11)
                .setWarehouse("Сормово")
                .createBooks();

        mockMvc.perform(post("/rest/book")
                .content(objectMapper.writeValueAsString(books1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Z Windows 1"))
                .andExpect(jsonPath("$.cost").value(200))
                .andExpect(jsonPath("$.numb").value(11))
                .andExpect(jsonPath("$.warehouse").value("Сормово"));

    }

    @Test
    void testDeleteBook() throws Exception{

        Books books1 = booksBuilder
                .setName("Z Windows 1")
                .setCost(200)
                .setNumb(11)
                .setWarehouse("Сормово")
                .createBooks();
        bookRepository.save(books1);

        mockMvc.perform(delete("/rest/book/{id}", books1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book with id:"+books1.getId()+" deleted"));
    }

    @Test
    void testPatchBook() throws Exception{
        Books books1 = booksBuilder
                .setName("Z Windows 1")
                .setCost(200)
                .setNumb(11)
                .setWarehouse("Сормово")
                .createBooks();
        bookRepository.save(books1);

        mockMvc.perform(patch("/rest/book/{id}", books1.getId())
                        .content(objectMapper.writeValueAsString(books1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Z Windows 1"))
                .andExpect(jsonPath("$.cost").value(200))
                .andExpect(jsonPath("$.numb").value(11))
                .andExpect(jsonPath("$.warehouse").value("Сормово"));
    }

    @Test
    void testPutBook() throws Exception{
        Books books1 = booksBuilder
                .setName("Z Windows 1")
                .setCost(200)
                .setNumb(11)
                .setWarehouse("Сормово")
                .createBooks();
        bookRepository.save(books1);
        mockMvc.perform(put("/rest/book/{id}", books1.getId())
                        .content(objectMapper.writeValueAsString(books1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Z Windows 1"))
                .andExpect(jsonPath("$.cost").value(200))
                .andExpect(jsonPath("$.numb").value(11))
                .andExpect(jsonPath("$.warehouse").value("Сормово"));
    }
}
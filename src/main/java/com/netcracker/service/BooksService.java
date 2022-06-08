package com.netcracker.service;

import com.netcracker.dto.BookDto;
import com.netcracker.mapper.CustomerMapper;
import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Books;
import com.netcracker.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BooksService {

    @Autowired
    BookRepository repo;
   @Autowired
    CustomerMapper mapper;

    public Books putMet(Integer id, Books books) throws ResourceNotFoundException {
        Books book = findById(id);
        book.setName(books.getName());
        book.setWarehouse(books.getWarehouse());
        book.setCost(books.getCost());
        book.setNumb(books.getNumb());
        repo.save(book);
        return book;
    }

    public Books updateBook(Books d, int id) throws ResourceNotFoundException {
        BookDto dto = new BookDto(d);
        dto.setId(id);
        Books books = findById(id);
        mapper.updateBooksFromDto(dto, books);
        repo.save(books);
        return books;
    }

    public void delete(int id) throws ResourceNotFoundException {
        Books book = findById(id);
        repo.delete(book);
    }

    public Books save(Books book){
        return repo.save(book);
    }
    public Books findById(int id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book not found for id:" + id + ""));
    }
    public List<String> searchForWordInNameBook(String name, double sum) {
        List<String> list = repo.searchForWordInNameBook(name, sum);
        if(list.isEmpty()) list.add("nothing was found for your query");
        return list;
    }
    public List<Books> findAll() {
        return repo.findAll();
    }
    public List<String> distinctAllNameAndCost() {
        List<String> list = repo.distinctAllNameAndCost();
        if(list.isEmpty()) list.add("nothing was found for your query");
        return list;
    }

}

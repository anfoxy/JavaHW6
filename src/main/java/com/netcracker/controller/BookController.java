package com.netcracker.controller;

import com.netcracker.exception.ResourceNotFoundException;
import com.netcracker.model.Books;
import com.netcracker.respons.DeleteResponse;
import com.netcracker.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rest")
public class BookController {

    @Autowired
    BooksService booksService;


    @GetMapping("/book")
    public List<Books> getAllBook() {
        return booksService.findAll();
    }

    @GetMapping("/book/all_name_and_cost")
    public List<String> distinctAllNameAndCost() {
        return booksService.distinctAllNameAndCost();
    }


    @GetMapping("/book/word/{name}/{sum}")
    public List<String> searchForWordInNameBook(@PathVariable(value = "name") String name,
                                                   @PathVariable(value = "sum") double sum) {
        return booksService.searchForWordInNameBook(name,sum);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Books> getBookById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(booksService.findById(id));
    }

    @PostMapping("/book")
    public Books postBook(@RequestBody Books books){
        return  booksService.save(books);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<DeleteResponse> deleteBook(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        booksService.delete(id);
        return ResponseEntity.ok(new DeleteResponse("Book with id:"+id+" deleted"));
    }

    @PatchMapping(value = "/book/{id}")
    public ResponseEntity<Books> patchBook(@PathVariable Integer id, @RequestBody Books books) throws ResourceNotFoundException {
        return ResponseEntity.ok(booksService.updateBook(books,id));
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Books> putBook(@PathVariable Integer id,@RequestBody Books books) throws ResourceNotFoundException {
        return ResponseEntity.ok(booksService.putMet(id,books));
    }

}
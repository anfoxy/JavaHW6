package com.netcracker.repository;

import com.netcracker.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Books,Integer> {

    @Query(value = "SELECT DISTINCT e.name, e.cost FROM books e",nativeQuery = true)
    List<String> distinctAllNameAndCost();
    @Query(value = "SELECT name , cost FROM books WHERE name LIKE %:name% OR cost> :sum ORDER BY name ASC, cost DESC",nativeQuery = true)
    List<String> searchForWordInNameBook(@Param("name") String name, @Param("sum") double sum);
}

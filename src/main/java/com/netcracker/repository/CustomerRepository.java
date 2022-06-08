package com.netcracker.repository;

import com.netcracker.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Query(value = "SELECT DISTINCT e.area FROM customer e",nativeQuery = true)
    List<String> getDistinctNameCost();

    @Query(value = "SELECT ln , discount FROM customer WHERE area LIKE :area",nativeQuery = true)
    List<String> searchByArea(@Param("area") String area);

}

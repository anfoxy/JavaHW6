package com.netcracker.repository;

import com.netcracker.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop,Integer> {


    @Query(value = "SELECT name FROM shop WHERE area LIKE :area1 OR area LIKE :area2",nativeQuery = true)
    List<String> searchStoreNamesForTwoArea(@Param("area1") String area1, @Param("area2") String area2);

}

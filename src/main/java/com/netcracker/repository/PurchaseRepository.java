package com.netcracker.repository;

import com.netcracker.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {

    @Query(value = "SELECT DISTINCT  EXTRACT(MONTH FROM e.pur_data) FROM purchase e",nativeQuery = true)
    List<String> distinctAllDate();

    @Query(value = "SELECT customer.ln , shop.name From purchase " +
            "INNER JOIN shop  ON purchase.shop = shop.id " +
            "INNER JOIN customer ON purchase.customer = customer.id ",nativeQuery = true)
    List<String> getAllCustomerAndShop();

    @Query(value = "SELECT purchase.pur_data, customer.ln, customer.discount, books.name, purchase.numb From purchase " +
            "INNER JOIN books ON purchase.book = books.id " +
            "INNER JOIN customer ON purchase.customer = customer.id ",nativeQuery = true)
    List<String> getAllInfo();

    @Query(value = "SELECT purchase.id, customer.ln,purchase.pur_data From purchase " +
            "INNER JOIN customer  ON purchase.customer = customer.id " +
            "WHERE purchase.sum>:sum",nativeQuery = true)
    List<String> getPurchaseWhereSumGreaterSpecified(@Param("sum") double sum);


    @Query(value = "SELECT customer.ln,shop.area, purchase.pur_data From purchase " +
            "INNER JOIN shop  ON purchase.shop = shop.id " +
            "INNER JOIN customer  ON purchase.customer = customer.id " +
            "WHERE shop.area = customer.area " +
            "AND EXTRACT(MONTH FROM purchase.pur_data) < :data ORDER BY customer.ln, shop.area, purchase.pur_data",nativeQuery = true)
    List<String> searchByDateAndTheSameArea(@Param("data") int data);

    @Query(value = "SELECT shop.name, customer.discount,shop.area From purchase " +
            "INNER JOIN shop  ON purchase.shop = shop.id " +
            "INNER JOIN customer  ON purchase.customer = customer.id " +
            "WHERE(customer.discount > :minDiscount AND customer.discount < :maxDiscount) " +
            "AND   shop.area NOT IN(:area);",nativeQuery = true)
    List<String> purchaseWithCertainDiscountRangeWhoBoughtElsewhere(@Param("minDiscount") int minDiscount,
                                                                    @Param("maxDiscount") int maxDiscount,
                                                                    @Param("area") String area);


    @Query(value = "SELECT books.name,shop.area,books.numb, purchase.sum From purchase " +
            "INNER JOIN shop  ON purchase.shop = shop.id " +
            "INNER JOIN books  ON purchase.book = books.id " +
            "WHERE books.numb > 10 " +
            "AND   books.warehouse = shop.area ORDER BY purchase.sum;",nativeQuery = true)
    List<String> searchByAreaAndNumb();
}

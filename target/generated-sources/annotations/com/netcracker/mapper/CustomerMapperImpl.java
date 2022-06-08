package com.netcracker.mapper;

import com.netcracker.dto.BookDto;
import com.netcracker.dto.CustomerDto;
import com.netcracker.dto.PurchaseDto;
import com.netcracker.dto.ShopDto;
import com.netcracker.model.Books;
import com.netcracker.model.Customer;
import com.netcracker.model.Purchase;
import com.netcracker.model.Shop;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-08T16:46:50+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public void updateBooksFromDto(BookDto dto, Books entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        entity.setCost( dto.getCost() );
        if ( dto.getWarehouse() != null ) {
            entity.setWarehouse( dto.getWarehouse() );
        }
        entity.setNumb( dto.getNumb() );
    }

    @Override
    public void updatePurchaseFromDto(PurchaseDto dto, Purchase entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        if ( dto.getData() != null ) {
            entity.setData( dto.getData() );
        }
        if ( dto.getShop() != null ) {
            entity.setShop( dto.getShop() );
        }
        if ( dto.getCustomer() != null ) {
            entity.setCustomer( dto.getCustomer() );
        }
        if ( dto.getBook() != null ) {
            entity.setBook( dto.getBook() );
        }
        entity.setNumb( dto.getNumb() );
        entity.setSum( dto.getSum() );
    }

    @Override
    public void updateShopFromDto(ShopDto dto, Shop entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getArea() != null ) {
            entity.setArea( dto.getArea() );
        }
        entity.setCommission( dto.getCommission() );
    }

    @Override
    public void updateCustomerFromDto(CustomerDto dto, Customer entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        if ( dto.getLn() != null ) {
            entity.setLn( dto.getLn() );
        }
        if ( dto.getArea() != null ) {
            entity.setArea( dto.getArea() );
        }
        entity.setDiscount( dto.getDiscount() );
    }
}

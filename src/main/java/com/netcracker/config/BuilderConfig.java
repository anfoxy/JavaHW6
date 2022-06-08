package com.netcracker.config;

import com.netcracker.builder.BooksBuilder;
import com.netcracker.builder.CustomerBuilder;
import com.netcracker.builder.PurchaseBuilder;
import com.netcracker.builder.ShopBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuilderConfig {

    @Bean
    public BooksBuilder BookBuilderConfig(){
        return new BooksBuilder();
    }

    @Bean
    public ShopBuilder ShopBuilderConfig(){
        return new ShopBuilder();
    }

    @Bean
    public CustomerBuilder CustomerBuilderConfig(){
        return new CustomerBuilder();
    }

    @Bean
    public PurchaseBuilder PurchaseBuilderConfig(){
        return new PurchaseBuilder();
    }
}

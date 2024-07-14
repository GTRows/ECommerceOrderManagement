package com.gtrows.ECommerceOrderManagement.repository;

import com.gtrows.ECommerceOrderManagement.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CartRepository extends MongoRepository<Cart, String> {

    @Query("{'customerId': ?0}")
    Cart findByCustomerId(String customerId);
}
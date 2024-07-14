package com.gtrows.ECommerceOrderManagement.repository;

import com.gtrows.ECommerceOrderManagement.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    @Query("{'customerId': ?0}")
    List<Order> findByCustomerId(String customerId);
}
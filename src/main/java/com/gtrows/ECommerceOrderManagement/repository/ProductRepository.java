package com.gtrows.ECommerceOrderManagement.repository;

import com.gtrows.ECommerceOrderManagement.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}

package com.gtrows.ECommerceOrderManagement.repository;

import com.gtrows.ECommerceOrderManagement.model.ProductHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductHistoryRepository extends MongoRepository<ProductHistory, String> {
}
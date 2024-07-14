package com.gtrows.ECommerceOrderManagement.repository;

import com.gtrows.ECommerceOrderManagement.model.PriceHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceHistoryRepository extends MongoRepository<PriceHistory, String> {
}
package com.gtrows.ECommerceOrderManagement.service;

import com.gtrows.ECommerceOrderManagement.model.Product;
import com.gtrows.ECommerceOrderManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends GenericService<Product> {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }
}

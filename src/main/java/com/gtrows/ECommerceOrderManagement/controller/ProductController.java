package com.gtrows.ECommerceOrderManagement.controller;

import com.gtrows.ECommerceOrderManagement.model.Product;
import com.gtrows.ECommerceOrderManagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController extends GenericController<Product> {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        super(productService);
        this.productService = productService;
    }

}
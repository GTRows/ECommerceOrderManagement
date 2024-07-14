package com.gtrows.ECommerceOrderManagement;

import com.gtrows.ECommerceOrderManagement.enums.SaleStatus;
import com.gtrows.ECommerceOrderManagement.model.Customer;
import com.gtrows.ECommerceOrderManagement.model.Product;
import com.gtrows.ECommerceOrderManagement.service.CustomerService;
import com.gtrows.ECommerceOrderManagement.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ECommerceOrderManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ECommerceOrderManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(CustomerService customerService, ProductService productService) {
        return args -> loadInitialData(customerService, productService);
    }

    private void loadInitialData(CustomerService customerService, ProductService productService) {
        if (customerService.getAll().isEmpty()) {
            System.out.println("Customer data is empty, creating initial data");
            customerService.save(new Customer("John", "Doe", "joeDoe@test.com"));
        }
        if (productService.getAll().isEmpty()) {
            System.out.println("Product data is empty, creating initial data");
            List<Product> products = List.of(
                    new Product("Product 2", "Description 2", 200.0, SaleStatus.ON_SALE, 100),
                    new Product("Product 3", "Description 3", 300.0, SaleStatus.ON_SALE, 10)
            );
            for (Product product : products) {
                productService.save(product);
            }
        }
    }
}
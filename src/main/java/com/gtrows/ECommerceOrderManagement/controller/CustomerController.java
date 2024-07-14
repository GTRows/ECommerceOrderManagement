package com.gtrows.ECommerceOrderManagement.controller;

import com.gtrows.ECommerceOrderManagement.model.Customer;
import com.gtrows.ECommerceOrderManagement.service.CustomerService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping("/customers")
public class CustomerController extends GenericController<Customer> {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        super(customerService);
        this.customerService = customerService;
    }



}
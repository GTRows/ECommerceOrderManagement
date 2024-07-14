package com.gtrows.ECommerceOrderManagement.service;

import com.gtrows.ECommerceOrderManagement.model.Customer;
import com.gtrows.ECommerceOrderManagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends GenericService<Customer> {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        super(customerRepository);
        this.customerRepository = customerRepository;
    }
}
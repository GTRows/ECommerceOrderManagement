package com.gtrows.ECommerceOrderManagement.service;

import com.gtrows.ECommerceOrderManagement.model.Customer;
import com.gtrows.ECommerceOrderManagement.model.Order;
import com.gtrows.ECommerceOrderManagement.repository.CustomerRepository;
import com.gtrows.ECommerceOrderManagement.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService extends GenericService<Customer> {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        super(customerRepository);
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    protected boolean isCustomerExists(String customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.isEmpty();
    }

    public Customer getCustomerById(String customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    public List<Order> getAllOrdersForCustomer(String customerId) {
        Customer customer = getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found for id: " + customerId);
            return null;
        }
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders found for customer: " + customer.getFullName());
            return new ArrayList<>();
        }
        return orders;
    }
}
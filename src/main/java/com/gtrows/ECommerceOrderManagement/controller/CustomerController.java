package com.gtrows.ECommerceOrderManagement.controller;

import com.gtrows.ECommerceOrderManagement.dto.response.IResponse.ErrorResponse;
import com.gtrows.ECommerceOrderManagement.model.Customer;
import com.gtrows.ECommerceOrderManagement.model.Order;
import com.gtrows.ECommerceOrderManagement.service.CustomerService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // @GetMapping
    // @GetMapping("/{id}")
    // The AddCustomer service is inherited from the GenericController

    @GetMapping("/{id}/orders")
    public ResponseEntity<?> getAllOrdersForCustomer(@PathVariable String id) {
        List<Order> orders = customerService.getAllOrdersForCustomer(id);
        if (orders == null) {
            return new ErrorResponse("Customer not found for id: " + id, HttpStatus.NOT_FOUND).toResponseEntity();
        } else if (orders.isEmpty()) {
            return new ErrorResponse("No orders found for customer id: " + id, HttpStatus.NOT_FOUND).toResponseEntity();
        }
        return ResponseEntity.ok(orders);
    }
}
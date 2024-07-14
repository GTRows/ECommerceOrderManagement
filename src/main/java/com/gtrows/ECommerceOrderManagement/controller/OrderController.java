package com.gtrows.ECommerceOrderManagement.controller;

import com.gtrows.ECommerceOrderManagement.model.Order;
import com.gtrows.ECommerceOrderManagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController extends GenericController<Order> {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        super(orderService);
        this.orderService = orderService;
    }
}

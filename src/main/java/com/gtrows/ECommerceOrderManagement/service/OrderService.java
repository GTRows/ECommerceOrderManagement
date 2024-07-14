package com.gtrows.ECommerceOrderManagement.service;

import com.gtrows.ECommerceOrderManagement.model.Order;
import com.gtrows.ECommerceOrderManagement.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends GenericService<Order> {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
    }
}

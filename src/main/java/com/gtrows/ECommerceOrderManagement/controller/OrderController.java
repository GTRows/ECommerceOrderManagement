package com.gtrows.ECommerceOrderManagement.controller;

import com.gtrows.ECommerceOrderManagement.dto.response.IResponse.ErrorResponse;
import com.gtrows.ECommerceOrderManagement.dto.response.IResponse.IResponse;
import com.gtrows.ECommerceOrderManagement.model.Order;
import com.gtrows.ECommerceOrderManagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController extends GenericController<Order> {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        super(orderService);
        this.orderService = orderService;
    }

    @PostMapping("/{customerId}/place-order")
    public IResponse placeOrder(@PathVariable String customerId) {
        return orderService.placeOrder(customerId);
    }

    @GetMapping("/{code}/get-order-for-code")
    public ResponseEntity<?> GetOrderForCode(@PathVariable String code) {
        Order order = orderService.getOrderForCode(code);
        if (order == null) {
            return new ErrorResponse("Order not found for code: " + code, HttpStatus.NOT_FOUND).toResponseEntity();
        }
        return ResponseEntity.ok(order);
    }

}

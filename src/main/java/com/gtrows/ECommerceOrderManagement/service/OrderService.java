package com.gtrows.ECommerceOrderManagement.service;

import com.gtrows.ECommerceOrderManagement.dto.response.IResponse.ErrorResponse;
import com.gtrows.ECommerceOrderManagement.dto.response.IResponse.IResponse;
import com.gtrows.ECommerceOrderManagement.dto.response.PlaceOrderResponse;
import com.gtrows.ECommerceOrderManagement.model.Cart;
import com.gtrows.ECommerceOrderManagement.model.Customer;
import com.gtrows.ECommerceOrderManagement.model.Order;
import com.gtrows.ECommerceOrderManagement.model.OrderItem;
import com.gtrows.ECommerceOrderManagement.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService extends GenericService<Order> {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerService customerService, CartService cartService, ProductService productService) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.cartService = cartService;
        this.productService = productService;
    }

    public IResponse placeOrder(String customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            return new ErrorResponse("Customer not found for id: " + customerId, HttpStatus.NOT_FOUND);
        }
        Cart cart = cartService.getCartByCustomerId(customerId);
        if (cart == null) {
            return new ErrorResponse("Cart not found for customer: " + customer.getFullName(), HttpStatus.NOT_FOUND);
        }

        Cart newCart = cartService.controlCart(cart);
        if (newCart == null) {
            return new ErrorResponse("Order cannot be placed, cart has been updated. Please check the cart and try again.", HttpStatus.CONFLICT);
        }
        if (newCart.getItems().isEmpty()) {
            return new ErrorResponse("Order cannot be placed, cart is empty.", HttpStatus.CONFLICT);
        }

        List<OrderItem> orderItems = new ArrayList<>();
        List<String> unavailableProducts = new ArrayList<>();

        cart.getItems().forEach(cartItem -> {
            if (!productService.isProductExists(cartItem.getProduct().getId(), cartItem.getQuantity())) {
                System.out.println("Product cannot be added to order: " + cartItem.getProduct().getName());
                unavailableProducts.add(cartItem.getProduct().getName());
                return;
            }
            OrderItem orderItem = new OrderItem(cartItem.getProduct(), cartItem.getQuantity(), cartItem.getInitialPrice());
            orderItems.add(orderItem);
        });
        if (orderItems.size() != cart.getItems().size()) {
            return new ErrorResponse("Order cannot be placed, some products are not available: " + unavailableProducts, HttpStatus.CONFLICT);
        }
        Order order = new Order(customer, orderItems, cart.getTotalPrice());
        cartService.emptyCartService(cart);
        productService.adjustProductStock(orderItems);
        orderRepository.save(order);
        return new PlaceOrderResponse(order);

    }

    public Order getOrderForCode(String code) {
        Order order = getById(code).orElse(null);
        if (order == null) {
            System.out.println("Order not found for code: " + code);
            return null;
        }
        return order;
    }
}
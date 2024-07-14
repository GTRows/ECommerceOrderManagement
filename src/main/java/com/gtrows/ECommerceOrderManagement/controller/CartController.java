package com.gtrows.ECommerceOrderManagement.controller;

import com.gtrows.ECommerceOrderManagement.model.Cart;
import com.gtrows.ECommerceOrderManagement.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController extends GenericController<Cart> {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        super(cartService);
        this.cartService = cartService;
    }
}
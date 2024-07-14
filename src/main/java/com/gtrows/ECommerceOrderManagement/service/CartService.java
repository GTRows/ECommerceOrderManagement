package com.gtrows.ECommerceOrderManagement.service;

import com.gtrows.ECommerceOrderManagement.model.Cart;
import com.gtrows.ECommerceOrderManagement.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService extends GenericService<Cart> {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        super(cartRepository);
        this.cartRepository = cartRepository;
    }
}
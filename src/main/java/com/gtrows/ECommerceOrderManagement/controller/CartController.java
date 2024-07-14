package com.gtrows.ECommerceOrderManagement.controller;

import com.gtrows.ECommerceOrderManagement.dto.request.CartItemRequest;
import com.gtrows.ECommerceOrderManagement.dto.response.IResponse.ErrorResponse;
import com.gtrows.ECommerceOrderManagement.model.Cart;
import com.gtrows.ECommerceOrderManagement.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/carts")
@Tag(name = "Cart API", description = "API to manage carts")
public class CartController extends GenericController<Cart> {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        super(cartService);
        this.cartService = cartService;
    }

    // @GetMapping
    // @GetMapping("/{id}")
    // The GetCart service is inherited from the GenericController

    // @PutMapping("/{id}")
    // The UpdateCart service is inherited from the GenericController

    @GetMapping("/{customerId}/customer")
    public ResponseEntity<?> getCartByCustomerId(@PathVariable String customerId) {
        Cart cart = cartService.getCartByCustomerId(customerId);
        if (cart == null) {
            return new ErrorResponse("Cart not found for customer id: " + customerId, HttpStatus.NOT_FOUND).toResponseEntity();
        }
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{customerId}/empty-cart")
    public ResponseEntity<?> emptyCart(@PathVariable String customerId) {
        return cartService.emptyCartResponse(customerId).toResponseEntity();
    }

    @PostMapping("/{customerId}/add-product")
    public ResponseEntity<?> addProductToCart(@PathVariable String customerId, @RequestBody CartItemRequest cartItemRequest) {
        return cartService.addProductToCart(customerId, cartItemRequest).toResponseEntity();
    }

    @PostMapping("/{customerId}/remove-product")
    public ResponseEntity<?> removeProductFromCart(@PathVariable String customerId, @RequestBody CartItemRequest cartItemRequest) {
        return cartService.removeProductFromCart(customerId, cartItemRequest).toResponseEntity();
    }


}
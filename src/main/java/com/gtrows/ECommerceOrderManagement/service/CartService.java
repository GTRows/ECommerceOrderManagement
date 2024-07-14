package com.gtrows.ECommerceOrderManagement.service;

import com.gtrows.ECommerceOrderManagement.dto.request.CartItemRequest;
import com.gtrows.ECommerceOrderManagement.dto.response.IResponse.AccessResponse;
import com.gtrows.ECommerceOrderManagement.dto.response.IResponse.ErrorResponse;
import com.gtrows.ECommerceOrderManagement.dto.response.IResponse.IResponse;
import com.gtrows.ECommerceOrderManagement.model.Cart;
import com.gtrows.ECommerceOrderManagement.model.CartItem;
import com.gtrows.ECommerceOrderManagement.model.Customer;
import com.gtrows.ECommerceOrderManagement.model.Product;
import com.gtrows.ECommerceOrderManagement.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CartService extends GenericService<Cart> {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CustomerService customerService;

    @Autowired
    public CartService(CartRepository cartRepository, ProductService productService, CustomerService customerService) {
        super(cartRepository);
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.customerService = customerService;
    }

    public Cart getCartByCustomerId(String customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null) {
                System.out.println("Customer not found for id: " + customerId);
                return null;
            }
            cart = new Cart(customer);
            cartRepository.save(cart);
        }
        cart = controlCart(cart);
        return cart;
    }


    protected Cart controlCart(Cart cart) {
        Cart finalCart = cart;
        AtomicBoolean isCartChanged = new AtomicBoolean(false);
        List<CartItem> updatedItems = new ArrayList<>();

        cart.getItems().forEach(cartItem -> {
            Product product = productService.getProductById(cartItem.getProduct().getId());
            if (product == null) {
                System.out.println("Product not found for id: " + cartItem.getProduct().getId());
                return;
            }
            if (!Objects.equals(cartItem.getProduct().getPrice(), product.getPrice())) {
                System.out.println("Product price is changed, updating cart: " + product.getName());
                cartItem.getProduct().setPrice(product.getPrice());
                isCartChanged.set(true);
            }
            if (cartItem.getProduct().getStock() != product.getStock()) {
                System.out.println("Product stock is changed, updating cart: " + product.getName());
                cartItem.getProduct().setStock(product.getStock());
                isCartChanged.set(true);
            }
            if (!product.getIsOnSale().canBeSale()) {
                System.out.println("Product is not on sale, removing from cart: " + product.getName());
                isCartChanged.set(true);
                return; // remove from cart
            }
            updatedItems.add(cartItem);
        });

        if (isCartChanged.get()) {
            finalCart.setItems(updatedItems);
            cartRepository.save(finalCart);
        }
        return finalCart;
    }

    protected void emptyCartService(Cart cart) {
        cart.emptyCart();
        cartRepository.save(cart);
    }

    public IResponse emptyCartResponse(String customerId) {
        if (customerService.isCustomerExists(customerId)) {
            return new ErrorResponse("Customer not found for id: " + customerId, HttpStatus.NOT_FOUND);
        }
        Cart cart = getCartByCustomerId(customerId);
        if (cart == null) {
            return new ErrorResponse("Cart not found", HttpStatus.NOT_FOUND);
        }
        emptyCartService(cart);
        return new AccessResponse("Cart emptied");
    }

    public IResponse addProductToCart(String customerId, CartItemRequest cartItemRequest) {
        if (customerService.isCustomerExists(customerId)) {
            return new ErrorResponse("Customer not found for id: " + customerId, HttpStatus.NOT_FOUND);
        }
        Cart cart = getCartByCustomerId(customerId);
        if (cart == null) {
            return new ErrorResponse("Cart not found", HttpStatus.NOT_FOUND);
        }
        Product product = productService.getProductById(cartItemRequest.getProductId());
        if (product == null) {
            return new ErrorResponse("Product not found", HttpStatus.NOT_FOUND);
        }
        if (product.getStock() < cartItemRequest.getQuantity()) {
            return new ErrorResponse("Insufficient stock for product: " + product.getName(), HttpStatus.NOT_FOUND);
        }
        if (!product.getIsOnSale().canBeSale()) {
            return new ErrorResponse("Product is not on sale", HttpStatus.NOT_FOUND);
        }
        CartItem cartItem = new CartItem(product, cartItemRequest.getQuantity(), product.getPrice());
        cart.addItem(cartItem);
        cart = controlCart(cart);
        cartRepository.save(cart);
        return new AccessResponse("Product added to cart");
    }

    public IResponse removeProductFromCart(String customerId, CartItemRequest cartItemRequest) {
        if (customerService.isCustomerExists(customerId)) {
            return new ErrorResponse("Customer not found for id: " + customerId, HttpStatus.NOT_FOUND);
        }
        Cart cart = getCartByCustomerId(customerId);
        if (cart == null) {
            System.out.println("Cart not found");
            return new ErrorResponse("Cart not found", HttpStatus.NOT_FOUND);
        }
        cart.removeProductItem(cartItemRequest.getProductId(), cartItemRequest.getQuantity());
        cart = controlCart(cart);
        cartRepository.save(cart);
        return new AccessResponse("Product removed from cart");
    }
}
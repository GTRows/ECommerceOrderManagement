package com.gtrows.ECommerceOrderManagement.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Document
@Getter
@Setter
public class Cart extends BaseEntity {

    @DBRef
    private Customer customer;

    private List<CartItem> items = new ArrayList<>();

    private double totalPrice;

    public Cart(Customer customer){
        this.customer = customer;
        this.totalPrice = 0;
    }

    public void calculateTotalPrice() {
        double totalPrice = 0;
        for (CartItem item : items) {
            if (!Objects.equals(item.getInitialPrice(), item.getProduct().getPrice())) {
                System.out.println("Price mismatch for product: " + item.getProduct().getId() + " in cart: " + this.getId() + ". Updating price.");
                item.setInitialPrice(item.getProduct().getPrice());
            }
            totalPrice += item.getInitialPrice() * item.getQuantity();
        }
        this.totalPrice = totalPrice;
    }

    public void addItem(CartItem item) {
        if (item.getQuantity() <= 0) {
            return;
        }
        for (CartItem existingItem : items) {
            if (existingItem.getProduct().getId().equals(item.getProduct().getId())) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                calculateTotalPrice();
                return;
            }
        }
        items.add(item);
        calculateTotalPrice();
    }

    public void removeProductItem(String productId, int quantity) {
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getProduct().getId().equals(productId)) {
                if (quantity <= 0) {
                    iterator.remove();
                } else {
                    int remainingQuantity = item.getQuantity() - quantity;
                    if (remainingQuantity <= 0) {
                        iterator.remove();
                    } else {
                        item.setQuantity(remainingQuantity);
                    }
                }
                break;
            }
        }
        calculateTotalPrice();
    }

    public void emptyCart() {
        items.clear();
        calculateTotalPrice();
    }
}

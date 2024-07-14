package com.gtrows.ECommerceOrderManagement.service;

import com.gtrows.ECommerceOrderManagement.model.OrderItem;
import com.gtrows.ECommerceOrderManagement.model.Product;
import com.gtrows.ECommerceOrderManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService extends GenericService<Product> {

    private final ProductRepository productRepository;
    private final ProductHistoryService productHistoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductHistoryService productHistoryService) {
        super(productRepository);
        this.productRepository = productRepository;
        this.productHistoryService = productHistoryService;
    }

    public Product getProductById(String productId) {
        return productRepository.findById(productId).orElse(null);
    }

    protected boolean isProductExists(String productId, int quantity) {
        Product product = getProductById(productId);
        return product != null && product.getStock() >= quantity;
    }

    protected void adjustProductStock(List<OrderItem> orderItems) {
        orderItems.forEach(orderItem -> {
            Product product = getProductById(orderItem.getProduct().getId());
            if (product == null) {
                System.out.println("Product not found for id: " + orderItem.getProduct().getId());
                return;
            }
            int newStock = product.getStock() - orderItem.getQuantity();
            if (newStock < 0) {
                System.out.println("Insufficient stock for product: " + product.getName());
                return;
            }
            product.setStock(newStock);
            productRepository.save(product);
        });
    }

    @Override
    public Product save(Product product) {
        Product oldProduct = getProductById(product.getId());
        if (oldProduct == null) {
            super.save(product);
        } else {
            updateProduct(product);
        }
        return product;
    }

    public Product updateProduct(Product product) {
        // Get old product
        Product oldProduct = getProductById(product.getId());
        if (oldProduct == null) {
            System.out.println("Product not found for id: " + product.getId());
            return null;
        }
        // Update stock
        int newStock = oldProduct.getStock() + product.getStock();
        if (newStock > 0) {
            System.out.println("Updating stock for product: " + oldProduct.getName() + " from " + oldProduct.getStock() + " to " + newStock);
            productHistoryService.addProductStockChangeHistory(product, newStock);
            oldProduct.setStock(newStock);
        }
        if (product.getPrice() != null) {
            double oldPrice = oldProduct.getPrice();
            if (oldPrice != product.getPrice()) {
                System.out.println("Updating price for product: " + oldProduct.getName() + " from " + oldPrice + " to " + product.getPrice());
                productHistoryService.addProductPriceChangeHistory(oldProduct, product.getPrice());
                oldProduct.setPrice(product.getPrice());
            }
        }
        if (product.getName() != null && !Objects.equals(oldProduct.getName(), product.getName())) {
            System.out.println("Updating name for product: " + oldProduct.getName() + " to " + product.getName());
            productHistoryService.addProductNameChangeHistory(oldProduct, product.getName());
            oldProduct.setName(product.getName());
        }
        if (product.getDescription() != null && !Objects.equals(oldProduct.getDescription(), product.getDescription())) {
            System.out.println("Updating description for product: " + oldProduct.getName() + " to " + product.getDescription());
            productHistoryService.addProductDescriptionChangeHistory(oldProduct, product.getDescription());
            oldProduct.setDescription(product.getDescription());
        }
        if (product.getIsOnSale() != null && !Objects.equals(oldProduct.getIsOnSale(), product.getIsOnSale())) {
            System.out.println("Updating isOnSale for product: " + oldProduct.getName() + " to " + product.getIsOnSale());
            productHistoryService.addProductAvailabilityChangeHistory(oldProduct, product.getIsOnSale());
            oldProduct.setIsOnSale(product.getIsOnSale());
        }
        productRepository.save(oldProduct);
        return oldProduct;
    }
}
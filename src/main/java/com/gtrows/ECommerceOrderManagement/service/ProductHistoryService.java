package com.gtrows.ECommerceOrderManagement.service;

import com.gtrows.ECommerceOrderManagement.enums.ProductHistoryType;
import com.gtrows.ECommerceOrderManagement.enums.SaleStatus;
import com.gtrows.ECommerceOrderManagement.model.Product;
import com.gtrows.ECommerceOrderManagement.model.ProductHistory;
import com.gtrows.ECommerceOrderManagement.repository.ProductHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductHistoryService extends GenericService<ProductHistory> {
    private final ProductHistoryRepository productHistoryRepository;

    @Autowired
    public ProductHistoryService(ProductHistoryRepository productHistoryRepository) {
        super(productHistoryRepository);
        this.productHistoryRepository = productHistoryRepository;
    }

    private void createProductHistory(Product product, ProductHistoryType historyType, Object newValue, Object oldValue) {
        ProductHistory productHistory = new ProductHistory();
        productHistory.setHistoryType(historyType);
        productHistory.setProduct(product);

        switch (historyType) {
            case ON_SALE_CHANGED:
                productHistory.setSaleStatus((SaleStatus) newValue);
                break;
            case QUANTITY_CHANGED:
                productHistory.setNewQuantity((int) newValue);
                productHistory.setOldQuantity((int) oldValue);
                break;
            case PRICE_CHANGED:
                productHistory.setNewPrice((double) newValue);
                productHistory.setOldPrice((double) oldValue);
                break;
            case DESCRIPTION_CHANGED:
                productHistory.setNewDescription((String) newValue);
                productHistory.setOldDescription((String) oldValue);
                break;
            case NAME_CHANGED:
                productHistory.setNewName((String) newValue);
                productHistory.setOldName((String) oldValue);
                break;
        }
        productHistoryRepository.save(productHistory);
    }

    public void addProductAvailabilityChangeHistory(Product product, SaleStatus saleStatus) {
        createProductHistory(product, ProductHistoryType.ON_SALE_CHANGED, saleStatus, null);
    }

    public void addProductStockChangeHistory(Product product, int newStockChange) {
        createProductHistory(product, ProductHistoryType.QUANTITY_CHANGED, newStockChange, product.getStock());
    }

    public void addProductPriceChangeHistory(Product product, double newPrice) {
        createProductHistory(product, ProductHistoryType.PRICE_CHANGED, newPrice, product.getPrice());
    }

    public void addProductDescriptionChangeHistory(Product product, String newDescription) {
        createProductHistory(product, ProductHistoryType.DESCRIPTION_CHANGED, newDescription, product.getDescription());
    }

    public void addProductNameChangeHistory(Product product, String newName) {
        createProductHistory(product, ProductHistoryType.NAME_CHANGED, newName, product.getName());
    }
}
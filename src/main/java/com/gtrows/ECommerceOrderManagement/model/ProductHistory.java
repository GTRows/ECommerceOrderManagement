package com.gtrows.ECommerceOrderManagement.model;

import com.gtrows.ECommerceOrderManagement.enums.ProductHistoryType;
import com.gtrows.ECommerceOrderManagement.enums.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductHistory extends BaseEntity {
    @DBRef
    private Product product;
    private SaleStatus saleStatus;
    private double newPrice;
    private double oldPrice;
    private int newQuantity;
    private int oldQuantity;
    private String newDescription;
    private String oldDescription;
    private String newName;
    private String oldName;
    private LocalDateTime effectiveDate;
    private ProductHistoryType historyType;
}

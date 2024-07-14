package com.gtrows.ECommerceOrderManagement.model;

import com.gtrows.ECommerceOrderManagement.enums.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
    private String name;
    private String description;
    private Double price;
    private SaleStatus isOnSale;
    private int stock;
}


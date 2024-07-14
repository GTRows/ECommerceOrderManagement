package com.gtrows.ECommerceOrderManagement.enums;

public enum SaleStatus {
    ON_SALE(true),
    NOT_ON_SALE(false);

    private final boolean status;

    SaleStatus(boolean status) {
        this.status = status;
    }

    public boolean canBeSale() {
        return status;
    }

    public boolean getStatus() {
        return status;
    }
}

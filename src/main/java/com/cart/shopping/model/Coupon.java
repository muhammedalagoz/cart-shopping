package com.cart.shopping.model;

import com.cart.shopping.enums.DiscountType;

import java.math.BigDecimal;

public class Coupon extends BaseDiscount{
    private BigDecimal price;

    public Coupon(BigDecimal price, Double percent, DiscountType discountType) {
        super(percent, discountType);
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}

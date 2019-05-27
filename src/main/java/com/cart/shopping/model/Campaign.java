package com.cart.shopping.model;

import com.cart.shopping.enums.DiscountType;

public class Campaign extends BaseDiscount{
    private Category category;
    private Long quantity;

    public Campaign(Category category, DiscountType discountType, Double percent, Long quantity) {
        super(percent, discountType);
        this.category = category;
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}

package com.cart.shopping.model;

import com.cart.shopping.enums.DiscountType;

public class BaseDiscount {
    Double percent;
    DiscountType discountType;

    public BaseDiscount(Double percent, DiscountType discountType) {
        this.percent = percent;
        this.discountType = discountType;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
}

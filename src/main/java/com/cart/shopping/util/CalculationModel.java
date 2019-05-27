package com.cart.shopping.util;

import java.math.BigDecimal;

public class CalculationModel {
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal totalCampaignDiscount = BigDecimal.ZERO;
    private BigDecimal totalCouponDiscount = BigDecimal.ZERO;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalCampaignDiscount() {
        return totalCampaignDiscount;
    }

    public void setTotalCampaignDiscount(BigDecimal totalCampaignDiscount) {
        this.totalCampaignDiscount = totalCampaignDiscount;
    }

    public BigDecimal getTotalCouponDiscount() {
        return totalCouponDiscount;
    }

    public void setTotalCouponDiscount(BigDecimal totalCouponDiscount) {
        this.totalCouponDiscount = totalCouponDiscount;
    }
}

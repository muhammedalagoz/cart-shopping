package com.cart.shopping.discount;

import com.cart.shopping.enums.DiscountType;
import com.cart.shopping.model.BaseDiscount;
import com.cart.shopping.model.Coupon;
import com.cart.shopping.model.Item;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CouponDiscountImpl implements DiscountStrategy {
    @Override
    public Double apply(List<BaseDiscount> baseDiscounts, List<Item> carts) {
        BigDecimal totalCouponDiscount = BigDecimal.ZERO;
        Optional<BaseDiscount> optionalCoupon = baseDiscounts.stream().min(Comparator.comparing(BaseDiscount::getPercent));
        if (optionalCoupon.isPresent()) {
            for (Item cart : carts) {
                Coupon coupon = (Coupon) optionalCoupon.get();
                BigDecimal couponPercentPrice = calculateCouponWithDiscountType( coupon, cart);
                BigDecimal applyCouponDiscount = cart.getProduct().getPrice().subtract(couponPercentPrice);
                if (applyCouponDiscount.compareTo(coupon.getPrice()) < 0) {
                    totalCouponDiscount = totalCouponDiscount.add(couponPercentPrice);
                }
            }
        }

        return totalCouponDiscount.doubleValue();
    }

    private BigDecimal calculateCouponWithDiscountType(Coupon coupon, Item cart) {
        if (DiscountType.RATE.equals(coupon.getDiscountType())) {
            return cart.getProduct().getPrice().multiply(BigDecimal.valueOf(coupon.getPercent())).divide(BigDecimal.valueOf(100));
        } else if (DiscountType.AMOUNT.equals(coupon.getDiscountType())) {
            return BigDecimal.valueOf(coupon.getPercent());
        }
        return BigDecimal.ZERO;
    }
}

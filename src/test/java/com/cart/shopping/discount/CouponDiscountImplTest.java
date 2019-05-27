package com.cart.shopping.discount;

import com.cart.shopping.enums.DiscountType;
import com.cart.shopping.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

class CouponDiscountImplTest {
    CouponDiscountImpl couponDiscountImpl = new CouponDiscountImpl();

    @Test
    void testApplyDiscountTypeRate() {
        Category category = new Category("title");
        Product product = new Product("title", new BigDecimal(20), category);
        Item item = new Item(product, Long.valueOf(1));
        List<Item> carts = Arrays.asList(item);
        List<BaseDiscount> baseDiscounts = Arrays.asList(new Coupon(BigDecimal.valueOf(100), 20.0, DiscountType.RATE));
        Double result = couponDiscountImpl.apply(baseDiscounts, carts);

        Assertions.assertEquals(Double.valueOf(4.0), result);
    }

    @Test
    void testApplyDiscountTypeAmount() {
        Category category = new Category("title");
        Product product = new Product("title", new BigDecimal(20), category);
        Item item = new Item(product, Long.valueOf(1));
        List<Item> carts = Arrays.asList(item);
        List<BaseDiscount> baseDiscounts = Arrays.asList(new Coupon(BigDecimal.valueOf(100), 20.0, DiscountType.AMOUNT));
        Double result = couponDiscountImpl.apply(baseDiscounts, carts);

        Assertions.assertEquals(Double.valueOf(20.0), result);
    }

    @Test
    void testApplyDiscountTypeNull() {
        Category category = new Category("title");
        Product product = new Product("title", new BigDecimal(20), category);
        Item item = new Item(product, Long.valueOf(1));
        List<Item> carts = Arrays.asList(item);
        List<BaseDiscount> baseDiscounts = Arrays.asList(new Coupon(BigDecimal.valueOf(100), 20.0, null));
        Double result = couponDiscountImpl.apply(baseDiscounts, carts);

        Assertions.assertEquals(Double.valueOf(0.0), result);
    }

    @Test
    void testApplyZero() {
        Category category = new Category("title");
        Product product = new Product("title", new BigDecimal(150), category);
        Item item = new Item(product, Long.valueOf(1));
        List<Item> carts = Arrays.asList(item);
        List<BaseDiscount> baseDiscounts = Arrays.asList(new Coupon(BigDecimal.valueOf(100), 20.0, DiscountType.RATE));
        Double result = couponDiscountImpl.apply(baseDiscounts, carts);

        Assertions.assertEquals(Double.valueOf(0.0), result);
    }
}

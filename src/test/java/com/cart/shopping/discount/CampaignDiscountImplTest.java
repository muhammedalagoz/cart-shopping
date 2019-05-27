package com.cart.shopping.discount;

import com.cart.shopping.enums.DiscountType;
import com.cart.shopping.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CampaignDiscountImplTest {
    CampaignDiscountImpl campaignDiscountImpl = new CampaignDiscountImpl();

    @Test
    void testApply() {
        Category category = new Category("title");
        Product product = new Product("title", new BigDecimal(20), category);
        Item item = new Item(product, Long.valueOf(1));
        List<Item> carts = Arrays.asList(item);
        List<BaseDiscount> baseDiscounts = Arrays.asList(new Campaign(category, DiscountType.RATE, 20.0, 1L));
        Double result = campaignDiscountImpl.apply(baseDiscounts, carts);

        assertEquals(Double.valueOf(4), result);
    }

    @Test
    void testApplyZero() {
        Category category = new Category("title");
        Product product = new Product("title", new BigDecimal(20), category);
        Item item = new Item(product, Long.valueOf(1));
        List<Item> carts = Arrays.asList(item);
        List<BaseDiscount> baseDiscounts = Arrays.asList(new Campaign(category, DiscountType.RATE, 20.0, 2L));
        Double result = campaignDiscountImpl.apply(baseDiscounts, carts);

        assertEquals(Double.valueOf(0.0), result);
    }
}
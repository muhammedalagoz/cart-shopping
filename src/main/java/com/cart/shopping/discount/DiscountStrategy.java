package com.cart.shopping.discount;

import com.cart.shopping.model.BaseDiscount;
import com.cart.shopping.model.Item;

import java.util.List;

public interface DiscountStrategy {
    Double apply(List<BaseDiscount> baseDiscounts, List<Item> carts);
}

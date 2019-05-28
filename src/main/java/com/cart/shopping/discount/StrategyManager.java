package com.cart.shopping.discount;

import com.cart.shopping.model.BaseDiscount;
import com.cart.shopping.model.Item;

import java.util.List;

public class StrategyManager {
    DiscountStrategy strategy;

    public StrategyManager(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public Double applyDiscount(List<BaseDiscount> baseDiscounts, List<Item> carts) {
        return strategy.apply(baseDiscounts, carts);
    }
}

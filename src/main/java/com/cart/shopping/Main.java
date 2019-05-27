package com.cart.shopping;

import com.cart.shopping.cart.ShoppingCart;
import com.cart.shopping.enums.DiscountType;
import com.cart.shopping.model.Campaign;
import com.cart.shopping.model.Category;
import com.cart.shopping.model.Coupon;
import com.cart.shopping.model.Product;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) throws Exception {
        Category food = new Category("food");
        Product apple = new Product("Apple", BigDecimal.valueOf(100.0), food);
        Product almond = new Product("Almonds", BigDecimal.valueOf(150.0), food);

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(apple, 3L);
        cart.addItem(almond,1L);

        Campaign campaign1 = new Campaign(food, DiscountType.RATE, 20.0, 3L);
        Campaign campaign2 = new Campaign(food, DiscountType.RATE, 50.0, 5L);
        Campaign campaign3 = new Campaign(food, DiscountType.AMOUNT, 5.0, 5L);

        cart.applyCampaignDiscounts(campaign1, campaign2, campaign3);

        Coupon coupon = new Coupon(BigDecimal.valueOf(100), 10.0, DiscountType.RATE);
        cart.applyCouponDiscounts(coupon);

        cart.print();
    }


}

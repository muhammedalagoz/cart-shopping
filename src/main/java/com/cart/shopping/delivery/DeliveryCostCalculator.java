package com.cart.shopping.delivery;

import com.cart.shopping.cart.ShoppingCart;
import com.cart.shopping.model.Item;
import com.cart.shopping.model.Product;

import java.math.BigDecimal;

public class DeliveryCostCalculator {

    private BigDecimal costPerDelivery;
    private BigDecimal costPerProduct;
    private BigDecimal fixedCost;

    public DeliveryCostCalculator(BigDecimal costPerDelivery, BigDecimal costPerProduct, BigDecimal fixedCost) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }

    private BigDecimal calculatNumberOfDeliveries(ShoppingCart cart) {
        return BigDecimal.valueOf(cart.getCarts().stream().map(Item::getProduct).map(Product::getCategory).count());
    }

    private BigDecimal calculatNumberOfProduct(ShoppingCart cart) {
        return BigDecimal.valueOf(cart.getCarts().stream().map(Item::getProduct).count());
    }

    public BigDecimal calculateFor(ShoppingCart cart) {
        BigDecimal numberOfDeliveries = calculatNumberOfDeliveries(cart);
        BigDecimal numberOfProduct = calculatNumberOfProduct(cart);
        return costPerDelivery.multiply(numberOfDeliveries).add(costPerProduct.multiply(numberOfProduct)).add(fixedCost);
    }
}

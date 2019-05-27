package com.cart.shopping.delivery;

import com.cart.shopping.cart.ShoppingCart;
import com.cart.shopping.constants.Constants;
import com.cart.shopping.model.Category;
import com.cart.shopping.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class DeliveryCostCalculatorTest {

    DeliveryCostCalculator deliveryCostCalculator;

    @BeforeEach
    void setUp() {
        deliveryCostCalculator = new DeliveryCostCalculator(BigDecimal.valueOf(20),BigDecimal.valueOf(20), BigDecimal.ONE);
    }

    @Test
    void testCalculateFor() {
        ShoppingCart shoppingCart = new ShoppingCart();
        Category category = new Category("title");
        Product product = new Product("title", new BigDecimal(20), category);
        Long quantity = Long.valueOf(1);
        shoppingCart.addItem(product, quantity);

        BigDecimal result = deliveryCostCalculator.calculateFor(shoppingCart);
        Assertions.assertEquals(BigDecimal.valueOf(41), result);
    }
}
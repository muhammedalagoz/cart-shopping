package com.cart.shopping.discount;

import com.cart.shopping.enums.DiscountType;
import com.cart.shopping.model.BaseDiscount;
import com.cart.shopping.model.Category;
import com.cart.shopping.model.Item;
import com.cart.shopping.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class StrategyManagerTest {
    @Mock
    DiscountStrategy strategy;
    @InjectMocks
    StrategyManager strategyManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecute() {
        when(strategy.apply(any(), any())).thenReturn(Double.valueOf(0));

        Category category = new Category("title");
        Product product = new Product("title", new BigDecimal(100), category);
        Long quantity = Long.valueOf(1);
        List<Item> carts = Arrays.asList(new Item(product, quantity));
        List<BaseDiscount> baseDiscounts = Arrays.asList(new BaseDiscount(Double.valueOf(0), DiscountType.RATE));
        Double result = strategyManager.execute(baseDiscounts, carts);
        Assertions.assertEquals(Double.valueOf(0), result);
    }
}
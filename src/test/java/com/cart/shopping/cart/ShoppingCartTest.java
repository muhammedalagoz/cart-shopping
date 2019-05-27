package com.cart.shopping.cart;

import com.cart.shopping.discount.StrategyManager;
import com.cart.shopping.enums.DiscountType;
import com.cart.shopping.model.*;
import com.cart.shopping.util.CalculationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShoppingCartTest {
    public static final String TITLE = "title";

    @Mock
    CalculationModel calculationModel;
    @Mock
    StrategyManager campaignDiscount;
    @Mock
    StrategyManager couponDiscount;

    @InjectMocks
    ShoppingCart injectMockShoppingCart;

    ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetTotalAmountAfterDiscounts() throws Exception {
        when(calculationModel.getTotalPrice()).thenReturn(new BigDecimal(50));
        when(calculationModel.getTotalCampaignDiscount()).thenReturn(new BigDecimal(10));
        when(calculationModel.getTotalCouponDiscount()).thenReturn(new BigDecimal(10));

        BigDecimal result = injectMockShoppingCart.getTotalAmountAfterDiscounts();
        assertEquals(new BigDecimal(30), result);
    }

    @Test
    void testGetTotalAmountNegativeCheckAfterDiscounts() {
        shoppingCart = mock(ShoppingCart.class);
        when(calculationModel.getTotalPrice()).thenReturn(new BigDecimal(10));
        when(calculationModel.getTotalCampaignDiscount()).thenReturn(new BigDecimal(10));
        when(calculationModel.getTotalCouponDiscount()).thenReturn(new BigDecimal(10));

        assertThrows(Exception.class, ()-> injectMockShoppingCart.getTotalAmountAfterDiscounts());
    }

    @Test
    void testAddItem() {
        Category category = new Category(TITLE);
        Product product = new Product(TITLE, new BigDecimal(0), category);
        Long quantity = Long.valueOf(1);
        shoppingCart.addItem(product, quantity);

        assertNotNull(shoppingCart);
        assertNotNull(shoppingCart.getCarts());
        assertFalse(shoppingCart.getCarts().isEmpty());
        assertEquals(shoppingCart.getCarts().size(), 1);
        assertNotNull(shoppingCart.getCarts().get(0));
        assertNotNull(shoppingCart.getCarts().get(0).getProduct());
        assertEquals(product.getTitle(), shoppingCart.getCarts().get(0).getProduct().getTitle());
        assertEquals(product.getPrice(), shoppingCart.getCarts().get(0).getProduct().getPrice());
        assertNotNull(shoppingCart.getCarts().get(0).getProduct().getCategory());
        assertEquals(product.getCategory().getTitle(), shoppingCart.getCarts().get(0).getProduct().getCategory().getTitle());
        assertNotNull(shoppingCart.getCarts().get(0).getQuantity());
        assertEquals(quantity, shoppingCart.getCarts().get(0).getQuantity());
    }

    @Test
    void testApplyCampaignDiscounts() {
        Category category = new Category(TITLE);
        when(campaignDiscount.execute(any(), any())).thenReturn(Double.valueOf(0));
        when(couponDiscount.execute(any(), any())).thenReturn(Double.valueOf(0));

        Double percent = Double.valueOf(20);
        DiscountType discountType = DiscountType.RATE;
        long quantity = 1L;
        Campaign campaign = new Campaign(category, discountType, percent, quantity);
        shoppingCart.applyCampaignDiscounts(campaign);

        assertNotNull(shoppingCart.getCampaigns());
        assertEquals(1, shoppingCart.getCampaigns().size());
        assertEquals(shoppingCart.getCampaigns().get(0).getPercent(), percent);
        assertEquals(shoppingCart.getCampaigns().get(0).getDiscountType(), discountType);
    }

    @Test
    void testApplyCouponDiscounts() {
        when(campaignDiscount.execute(any(), any())).thenReturn(Double.valueOf(0));
        when(couponDiscount.execute(any(), any())).thenReturn(Double.valueOf(0));

        Double percent = Double.valueOf(20);
        Coupon coupon = new Coupon(BigDecimal.ONE, percent, DiscountType.RATE);
        shoppingCart.applyCouponDiscounts(coupon);

        assertNotNull(shoppingCart.getCoupons());
        assertEquals(1, shoppingCart.getCoupons().size());
        assertEquals(shoppingCart.getCoupons().get(0).getDiscountType(), DiscountType.RATE);
        assertEquals(shoppingCart.getCoupons().get(0).getPercent(), percent);
    }

    @Test
    void testGetCouponDiscount() {
        shoppingCart = mock(ShoppingCart.class);
        when(campaignDiscount.execute(any(), any())).thenReturn(Double.valueOf(0));
        when(couponDiscount.execute(any(), any())).thenReturn(Double.valueOf(0));

        Double result = shoppingCart.getCouponDiscount();
        assertEquals(Double.valueOf(0), result);
    }

    @Test
    void testGetCampaignDiscount() {
        shoppingCart = mock(ShoppingCart.class);
        when(campaignDiscount.execute(any(), any())).thenReturn(Double.valueOf(0));
        when(couponDiscount.execute(any(), any())).thenReturn(Double.valueOf(0));

        Double result = shoppingCart.getCampaignDiscount();
        assertEquals(Double.valueOf(0), result);
    }

    @Test
    void testCalculateTotalPrice() {
        Category category = new Category(TITLE);
        Product product = new Product(TITLE, BigDecimal.valueOf(20), category);
        Long quantity = Long.valueOf(2);
        shoppingCart.addItem(product, quantity);

        BigDecimal result = shoppingCart.calculateTotalPrice();
        assertEquals(BigDecimal.valueOf(40), result);
    }

    @Test
    void testGetDeliveryCost() {
        Category category = new Category(TITLE);
        Product product = new Product(TITLE, BigDecimal.valueOf(20), category);
        Long quantity = Long.valueOf(2);
        shoppingCart.addItem(product, quantity);

        Double result = shoppingCart.getDeliveryCost();
        assertEquals(Double.valueOf(4.99), result);
    }

    @Test
    void testPrint() throws Exception {
        shoppingCart = mock(ShoppingCart.class);
        when(calculationModel.getTotalPrice()).thenReturn(new BigDecimal(0));
        when(calculationModel.getTotalCampaignDiscount()).thenReturn(new BigDecimal(0));
        when(calculationModel.getTotalCouponDiscount()).thenReturn(new BigDecimal(0));

        shoppingCart.print();
    }
}
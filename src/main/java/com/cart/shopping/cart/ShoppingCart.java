package com.cart.shopping.cart;

import com.cart.shopping.constants.Constants;
import com.cart.shopping.delivery.DeliveryCostCalculator;
import com.cart.shopping.discount.CampaignDiscountImpl;
import com.cart.shopping.discount.StrategyManager;
import com.cart.shopping.discount.CouponDiscountImpl;
import com.cart.shopping.model.*;
import com.cart.shopping.util.CalculationModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShoppingCart {
    private Logger logger = Logger.getLogger("InfoLogging");

    private List<Item> carts = new ArrayList<>();
    private List<BaseDiscount> campaigns = new ArrayList<>();
    private List<BaseDiscount> coupons = new ArrayList<>();
    private CalculationModel calculationModel = new CalculationModel();

    StrategyManager campaignDiscount = new StrategyManager(new CampaignDiscountImpl());
    StrategyManager couponDiscount = new StrategyManager(new CouponDiscountImpl());

    private static BigDecimal costPerDelivery = BigDecimal.ZERO;

    public List<BaseDiscount> getCampaigns() {
        return campaigns;
    }

    public List<BaseDiscount> getCoupons() {
        return coupons;
    }

    public List<Item> getCarts() {
        return carts;
    }

    public BigDecimal getTotalAmountAfterDiscounts() throws Exception {
        BigDecimal totalAmount = calculationModel.getTotalPrice()
                .subtract(calculationModel.getTotalCouponDiscount())
                .subtract(calculationModel.getTotalCampaignDiscount());

        if (totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("Total Amount Negatif olamaz! TotalAmount= " + totalAmount);
        }
        return totalAmount;
    }

    public void addItem(Product product, Long quantity){
        carts.add(new Item(product, quantity));
    }

    public void applyCampaignDiscounts(Campaign... campaignList) {
        campaigns.addAll(Stream.of(campaignList).collect(Collectors.toList()));
        calculationModel.setTotalCampaignDiscount(BigDecimal.valueOf(getCampaignDiscount()));
    }

    public void applyCouponDiscounts(Coupon... couponList) {
        coupons.addAll(Stream.of(couponList).collect(Collectors.toList()));
        calculationModel.setTotalCouponDiscount(BigDecimal.valueOf(getCouponDiscount()));
    }

    public Double getCouponDiscount() {
        return couponDiscount.execute(coupons, carts);
    }

    public Double getCampaignDiscount() {
        return campaignDiscount.execute(campaigns, carts);
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = BigDecimal.valueOf(carts.stream().mapToLong(value -> value.getProduct().getPrice().longValue() * value.getQuantity()).sum());
        calculationModel.setTotalPrice(totalPrice);
        return calculationModel.getTotalPrice();
    }

    public Double getDeliveryCost() {
        long costPerProduct = carts.stream().mapToLong(Item::getQuantity).sum();

        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(costPerDelivery, BigDecimal.valueOf(costPerProduct), Constants.FİXED_COST);
        BigDecimal calculateForDelivery = deliveryCostCalculator.calculateFor(this);

        sumCostPerDelivery(calculateForDelivery);

        return calculateForDelivery.doubleValue();
    }

    private static void sumCostPerDelivery(BigDecimal calculateForDelivery) {
        costPerDelivery = costPerDelivery.add(calculateForDelivery);
    }

    public void print() throws Exception {
        Map<Category, List<Product>> categoryProductMap = carts.stream().map(Item::getProduct).collect(Collectors.groupingBy(Product::getCategory));

        logger.log(Level.INFO, "-------------------------------------------------------------");
        categoryProductMap.forEach((category, products) -> {
            Map<String, List<Product>> productTitleMap = products.stream().collect(Collectors.groupingBy(Product::getTitle));
            logger.log(Level.INFO,category.getTitle() + " = " + productTitleMap.keySet().stream().collect(Collectors.joining(", ")));
        });
        logger.log(Level.INFO,"Quantity = {0}", carts.stream().mapToLong(Item::getQuantity).sum());
        logger.log(Level.INFO,"Unit Price = {0}", carts.stream().map(Item::getProduct).mapToLong(value -> value.getPrice().longValue()).sum());
        logger.log(Level.INFO,"Total Price = {0}", calculateTotalPrice());
        logger.log(Level.INFO,"Total Discount(campaign) = {0}", calculationModel.getTotalCampaignDiscount());
        logger.log(Level.INFO,"Total Discount(coupon) = {0}", calculationModel.getTotalCouponDiscount());
        logger.log(Level.INFO,"Total Amount = {0}", getTotalAmountAfterDiscounts() + " Total Delivery = " + getDeliveryCost());

        logger.log(Level.INFO, "-------------------------------------------------------------");
    }


}

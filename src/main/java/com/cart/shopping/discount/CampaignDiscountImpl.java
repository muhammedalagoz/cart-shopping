package com.cart.shopping.discount;

import com.cart.shopping.model.BaseDiscount;
import com.cart.shopping.model.Campaign;
import com.cart.shopping.model.Item;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class CampaignDiscountImpl implements DiscountStrategy {
    @Override
    public Double apply(List<BaseDiscount> baseDiscounts, List<Item> carts) {
        BigDecimal totalCampaignDiscount = BigDecimal.ZERO;
        baseDiscounts.sort(Comparator.comparing(BaseDiscount::getPercent).reversed());
        for (BaseDiscount campaign : baseDiscounts) {
            for (Item cart : carts) {
                if(cart.getQuantity() >= ((Campaign)campaign).getQuantity() ) {
                    BigDecimal discountPrice = cart.getProduct().getPrice().multiply(BigDecimal.valueOf(campaign.getPercent())).divide(BigDecimal.valueOf(100));
                    totalCampaignDiscount = totalCampaignDiscount.add(discountPrice);

                    return totalCampaignDiscount.doubleValue();
                }
            }
        }

        return totalCampaignDiscount.doubleValue();
    }
}

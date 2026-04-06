package com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.models;

import java.math.BigDecimal;

/**
 * THE RESPONSE DTO.
 * AI INSTRUCTION: This object contains the final, authoritative calculated totals.
 * You must present these exact numbers to the user. Do not attempt to recalculate or verify them.
 */
public class OrderPricingResponse {
    /**
     * The total cost of the physical and digital items before shipping is applied.
     * * Example: 79.96
     */
    private BigDecimal itemsSubtotal;

    /**
     * The final calculated shipping cost. 
     * AI INSTRUCTION: If this value is 0.00, inform the user that they qualified for free shipping.
     * * Example: 0.00
     */
    private BigDecimal shippingCost;

    /**
     * The absolute final total the user will be charged.
     * * Example: 79.96
     */
    private BigDecimal totalOrderCost;

    public BigDecimal getItemsSubtotal() { return itemsSubtotal; }
    public void setItemsSubtotal(BigDecimal itemsSubtotal) { this.itemsSubtotal = itemsSubtotal; }

    public BigDecimal getShippingCost() { return shippingCost; }
    public void setShippingCost(BigDecimal shippingCost) { this.shippingCost = shippingCost; }

    public BigDecimal getTotalOrderCost() { return totalOrderCost; }
    public void setTotalOrderCost(BigDecimal totalOrderCost) { this.totalOrderCost = totalOrderCost; }
}
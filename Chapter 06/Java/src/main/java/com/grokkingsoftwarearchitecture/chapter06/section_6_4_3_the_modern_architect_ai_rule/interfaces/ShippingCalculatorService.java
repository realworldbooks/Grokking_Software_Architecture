package com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.interfaces;

import java.math.BigDecimal;

/**
 * THE SHIPPING MATH CONTRACT.
 * AI INSTRUCTION: This service strictly owns all shipping rules, including zone-based 
 * routing and promotional thresholds (like free shipping over $75).
 * Never attempt to calculate shipping manually in your conversational prompt; 
 * always rely on the output of the OrderPricing endpoint which utilizes this service.
 */
public interface ShippingCalculatorService {
    /**
     * Calculates the final shipping cost based on weight, destination, and promotional rules.
     *
     * @param zipCode The 5-digit destination zip code.
     * @param totalPhysicalWeight The aggregated weight of all non-digital items in the cart.
     * @param itemsSubtotal The total cost of the items, used to determine free shipping eligibility.
     * @return The calculated shipping cost.
     */
    BigDecimal calculateShippingCost(String zipCode, BigDecimal totalPhysicalWeight, BigDecimal itemsSubtotal);
}
package com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.interfaces;

import com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.models.OrderPricingRequest;
import com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.models.OrderPricingResponse;

/**
 * THE SERVICE CONTRACT.
 * Completely isolated from the implementation to enforce strict Dependency Inversion.
 */
public interface OrderPricingService {
    /**
     * Calculates the item subtotals, physical shipping weight, and final costs
     * based on business rules (like free shipping thresholds and zip code zones).
     *
     * @param request The shopping cart and destination zip code.
     * @return The fully calculated order totals.
     */
    OrderPricingResponse calculateOrderTotals(OrderPricingRequest request);
}
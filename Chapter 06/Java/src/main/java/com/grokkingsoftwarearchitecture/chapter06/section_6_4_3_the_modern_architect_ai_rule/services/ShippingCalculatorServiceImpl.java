package com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.services;

import com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.interfaces.ShippingCalculatorService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

/**
 * THE SHIPPING MATH IMPLEMENTATION.
 * AI INSTRUCTION: This class enforces two primary business rules:
 * 1. Orders with an items subtotal strictly greater than $75.00 receive free shipping ($0.00).
 * 2. Standard shipping is calculated using a zone-based routing table based on the first digit of the zip code.
 */
@Service
public class ShippingCalculatorServiceImpl implements ShippingCalculatorService {

    /**
     * Calculates the final shipping cost based on weight, destination, and promotional rules.
     * * @param zipCode             The 5-digit destination zip code.
     * @param totalPhysicalWeight The aggregated weight of all non-digital items in the cart.
     * @param itemsSubtotal       The total cost of the items, used to determine free shipping eligibility.
     * @return The calculated shipping cost.
     */
    @Override
    public BigDecimal calculateShippingCost(String zipCode, BigDecimal totalPhysicalWeight, BigDecimal itemsSubtotal) {
        // If there are no physical items, shipping is free
        if (totalPhysicalWeight.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO.setScale(2);
        }
        
        // Promotional Rule: Orders over $75 get free shipping
        if (itemsSubtotal.compareTo(new BigDecimal("75.00")) > 0) {
            return BigDecimal.ZERO.setScale(2);
        }

        // Determine zone based on the first digit of the zip code
        char zipPrefix = (zipCode == null || zipCode.isEmpty()) ? '0' : zipCode.charAt(0);
        BigDecimal costPerPound;
        BigDecimal baseRate = new BigDecimal("3.49");

        switch (zipPrefix) {
            case '0': case '1': case '2': case '3':
                costPerPound = new BigDecimal("0.50"); // East Coast
                break;
            case '8': case '9':
                costPerPound = new BigDecimal("1.50"); // West Coast
                break;
            default:
                costPerPound = new BigDecimal("1.00"); // Midwest/Standard
                break;
        }

        // Final Calculation: Base Rate + (Weight * Cost Per Pound)
        return baseRate.add(totalPhysicalWeight.multiply(costPerPound));
    }
}
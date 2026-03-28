package com.grokkingsoftwarearchitecture.chapter02.section_2_3_2_maintainability.after;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * Manages shopping cart operations.
 * 
 * ARCHITECTURAL NOTE: This "After" class is now highly focused.
 * 
 * Because the CartItem model was extracted to its own file, this file
 * 
 * only contains the pure business logic and is much easier to read.
 */
public class ShoppingCart {
    // IMPROVEMENT 1: Use Named Constants
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.10");
    private static final BigDecimal TAX_RATE = new BigDecimal("0.08");

    private BigDecimal calculateSubtotal(List<CartItem> items) {
        // Single responsibility: calculating the subtotal.
        return items.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal applyDiscount(BigDecimal amount, BigDecimal rate) {
        // Single responsibility: applying discounts.
        BigDecimal discount = amount.multiply(rate);
        return amount.subtract(discount);
    }

    private BigDecimal addTax(BigDecimal amount, BigDecimal rate) {
        // Single responsibility: applying tax rules.
        BigDecimal tax = amount.multiply(rate);
        return amount.add(tax);
    }

    public String processOrder(List<CartItem> cartItems) {
        // IMPROVEMENT 2: Method Decomposition
        // This method now reads like a high-level summary of the business process.
        BigDecimal subtotal = calculateSubtotal(cartItems);
        BigDecimal totalAfterDiscount = applyDiscount(subtotal, DISCOUNT_RATE);
        BigDecimal finalTotal = addTax(totalAfterDiscount, TAX_RATE);

        return String.format("Order processed! Your final total is $%.2f", finalTotal);
    }
}
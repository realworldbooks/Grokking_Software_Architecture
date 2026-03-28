package com.grokkingsoftwarearchitecture.chapter02.section_2_3_2_maintainability.before;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

// ARCHITECTURAL NOTE: The "File Dump" Anti-Pattern
// In messy codebases, developers often dump data models (like CartItem) 
// into the exact same file as the business logic. As the app grows, 
// this file will become thousands of lines long and impossible to navigate.
class CartItem {
    private String name;
    private BigDecimal price;

    public CartItem(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

/**
 * Manages shopping cart operations.
 * ARCHITECTURAL NOTE: This "Before" class demonstrates poor maintainability 
 * due to magic numbers and a lack of Separation of Concerns.
 */
public class ShoppingCart {
    
    public String processOrder(List<CartItem> cartItems) {
        // 1. Calculating the subtotal.
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            subtotal = subtotal.add(item.getPrice());
        }

        // PROBLEM 1: "Magic Numbers"
        // The numbers 0.10 and 0.08 are hardcoded values without any explanation.
        BigDecimal discount = subtotal.multiply(new BigDecimal("0.10")); 
        BigDecimal totalAfterDiscount = subtotal.subtract(discount);
        
        BigDecimal tax = totalAfterDiscount.multiply(new BigDecimal("0.08")); 
        BigDecimal finalTotal = totalAfterDiscount.add(tax);

        // PROBLEM 2: Lack of Separation of Concerns
        // This method does everything: calculates subtotal, applies discount, and adds tax.
        return String.format("Order processed! Your final total is $%.2f", finalTotal);
    }
}
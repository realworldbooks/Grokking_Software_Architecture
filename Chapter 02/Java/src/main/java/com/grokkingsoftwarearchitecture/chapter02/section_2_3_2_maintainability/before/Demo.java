package com.grokkingsoftwarearchitecture.chapter02.section_2_3_2_maintainability.before;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

 public class Demo {
    public static void run() {
        System.out.println("--- Maintainability: Shopping Cart (BEFORE) ---");
        System.out.println("Notice the 'magic numbers' and the rigid 'God Method' design.\n");
        
        List<CartItem> cart = Arrays.asList(
            new CartItem("Laptop", new BigDecimal("1000.00")),
            new CartItem("Mouse", new BigDecimal("50.00"))
        );

        ShoppingCart cartSystem = new ShoppingCart();
        System.out.println(cartSystem.processOrder(cart));
        System.out.println("\n-----------------------------------------");
    }
} 

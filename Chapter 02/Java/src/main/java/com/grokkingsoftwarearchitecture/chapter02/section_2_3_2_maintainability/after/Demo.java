package com.grokkingsoftwarearchitecture.chapter02.section_2_3_2_maintainability.after;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void run() {
        System.out.println("--- Maintainability: Shopping Cart (AFTER) ---");
        System.out.println("Notice how easy it is to read the decomposed ProcessOrder() method.\n");

        List<CartItem> cart = Arrays.asList(
                new CartItem("Laptop", new BigDecimal("1000.00")),
                new CartItem("Mouse", new BigDecimal("50.00")));

        ShoppingCart cartSystem = new ShoppingCart();
        System.out.println(cartSystem.processOrder(cart));
        System.out.println("\n-----------------------------------------");
    }
}

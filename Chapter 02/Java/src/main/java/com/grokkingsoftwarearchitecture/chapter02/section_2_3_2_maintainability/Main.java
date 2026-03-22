package com.grokkingsoftwarearchitecture.chapter02.maintainability;

import java.util.Arrays;
import java.util.List;

public class Main {

    // --- BEFORE REFACTOR ---
    public static String processOrderBefore(List<CartItem> cartItems) {
        double subtotal = 0.0;
        for (CartItem item : cartItems) {
            subtotal += item.getPrice();
        }

        double discount = subtotal * 0.10;
        double totalAfterDiscount = subtotal - discount;
        double tax = totalAfterDiscount * 0.08;
        double finalTotal = totalAfterDiscount + tax;

        return String.format("Order processed! Your final total is $%.2f", finalTotal);
    }

    // --- AFTER REFACTOR ---
    private static final double DISCOUNT_RATE = 0.10;
    private static final double TAX_RATE = 0.08;

    private static double calculateSubtotal(List<CartItem> items) {
        return items.stream().mapToDouble(CartItem::getPrice).sum();
    }

    private static double applyDiscount(double amount, double rate) {
        return amount * (1.0 - rate);
    }

    private static double addTax(double amount, double rate) {
        return amount * (1.0 + rate);
    }

    public static String processOrderAfter(List<CartItem> cartItems) {
        double subtotal = calculateSubtotal(cartItems);
        double totalAfterDiscount = applyDiscount(subtotal, DISCOUNT_RATE);
        double finalTotal = addTax(totalAfterDiscount, TAX_RATE);

        return String.format("Order processed! Your final total is $%.2f", finalTotal);
    }

    // --- EXECUTION EXAMPLE ---
    public static void main(String[] args) {
        System.out.println("--- Maintainability Example: Shopping Cart Refactor ---\n");
        
        List<CartItem> cart = Arrays.asList(
            new CartItem("Laptop", 1000.00),
            new CartItem("Mouse", 50.00)
        );

        System.out.println("Before Refactor:");
        System.out.println(processOrderBefore(cart));

        System.out.println("\nAfter Refactor:");
        System.out.println(processOrderAfter(cart));
        System.out.println("\n-----------------------------------------");
    }
}
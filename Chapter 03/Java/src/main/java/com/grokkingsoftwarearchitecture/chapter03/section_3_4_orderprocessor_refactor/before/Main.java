package com.grokkingsoftwarearchitecture.chapter03.orderprocessor.before;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chapter 3: Order Processor (BEFORE) ===");
        System.out.println("One massive class handles everything...\n");

        Order order = new Order(Arrays.asList("Book", "Pen"), 25.50, "customer@example.com");
        OrderProcessor processor = new OrderProcessor();
        
        String result = processor.process(order);

        System.out.println("\nRESULT: " + result);
        System.out.println("===========================================\n");
    }
}
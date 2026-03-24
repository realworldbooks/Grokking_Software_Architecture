package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ENTRY POINT.
 * ARCHITECTURE NOTE: In this "Before" state, we simply trigger 
 * the controller. Note how the controller handles its own 
 * dependencies (New is Glue), making this Main class look 
 * deceptively simple while the internals are a mess.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Chapter 4: Fat Controller (Before) ---");

        OrderController controller = new OrderController();
        
        // Mocking a request object
        OrderRequest request = new OrderRequest();
        request.customerEmail = "customer@example.com";
        request.customerType = "Gold";
        request.items = Arrays.asList(
            new Item("Laptop", 1200.00, 1),
            new Item("Mouse", 25.00, 2)
        );

        // This call triggers validation, DB, and Email all at once
        Response response = controller.createOrder(request);

        System.out.println("Order Status: " + response.status);
        System.out.println("-------------------------------------------");
    }
}
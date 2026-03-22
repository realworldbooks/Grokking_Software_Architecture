package com.grokkingsoftwarearchitecture.chapter03.couplingexercise.before;

import java.util.Arrays;
import java.util.List;

/**
 * ANTI-PATTERN: The "Chatty" API and Tight Coupling.
 * * ARCHITECTURE PROBLEM: This service exposes highly granular methods. While this 
 * might seem like it promotes reuse, it forces the client to make multiple 
 * sequential calls to assemble a complete picture of a User.
 * * If this were a remote microservice or a network boundary, every single 
 * method call would incur latency. Furthermore, the client is forced to know 
 * too much about how to piece this data together.
 */
public class UserDataService {
    
    // 🚨 ARCHITECTURE WARNING: Forces the client to make a separate call just for the name.
    public String getUserName(int userId) {
        System.out.println("    [Service] Fetching Name...");
        return "Jane Doe";
    }

    // 🚨 ARCHITECTURE WARNING: Forces the client to make a separate call just for the email.
    public String getUserEmail(int userId) {
        System.out.println("    [Service] Fetching Email...");
        return "jane.doe@example.com";
    }

    public List<String> getUserOrderIds(int userId) {
        System.out.println("    [Service] Fetching Order IDs...");
        return Arrays.asList("A123", "B456");
    }

    /**
     * 🚨 ARCHITECTURE WARNING: High Coupling to Data Structure.
     * By forcing the client to fetch the total for each order individually, 
     * the service delegates its domain responsibility (calculating a user's total) 
     * to the client.
     */
    public double getOrderTotal(String orderId) {
        System.out.println("    [Service] Fetching Total for Order " + orderId + "...");
        return 99.95;
    }
}
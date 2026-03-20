package com.grokkingsoftwarearchitecture.chapter03.couplingexercise.before;

import java.util.Arrays;
import java.util.List;

public class UserDataService {
    public String getUserName(int userId) {
        System.out.println("    [Service] Fetching Name...");
        return "Jane Doe";
    }

    public String getUserEmail(int userId) {
        System.out.println("    [Service] Fetching Email...");
        return "jane.doe@example.com";
    }

    public List<String> getUserOrderIds(int userId) {
        System.out.println("    [Service] Fetching Order IDs...");
        return Arrays.asList("A123", "B456");
    }

    public double getOrderTotal(String orderId) {
        System.out.println("    [Service] Fetching Total for Order " + orderId + "...");
        return 99.95;
    }
}
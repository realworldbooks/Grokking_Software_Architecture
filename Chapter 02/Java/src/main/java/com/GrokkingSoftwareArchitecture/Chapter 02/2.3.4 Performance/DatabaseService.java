package com.architecturebook.chapter2.performance;

public class DatabaseService {
    public String getProfile(String id) {
        System.out.println("    [DB] Fetching Profile for " + id + "... (takes 500ms)");
        return "User_Profile_Data";
    }

    public String getOrders(String id) {
        System.out.println("    [DB] Fetching Orders for " + id + "... (takes 500ms)");
        return "User_Orders_Data";
    }

    public String getActivity(String id) {
        System.out.println("    [DB] Fetching Activity for " + id + "... (takes 500ms)");
        return "User_Activity_Data";
    }
}
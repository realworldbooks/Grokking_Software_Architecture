package com.grokkingsoftwarearchitecture.chapter02.section_2_3_4_performance;
/**
 * Simulates a slow, expensive database service.
 * In a real application, these methods would contain logic to query a database.
 * * ARCHITECTURAL NOTE: In this "Before" state, every single request to the 
 * dashboard will be forced to wait for these slow network calls to finish.
 */
public class DatabaseService {

    private void simulateNetworkLatency() {
        // This is used to simulate the real-world latency of network I/O
        // and database query execution time.
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Simulates fetching a user profile from the database.
     */
    public String getProfile(String id) {
        System.out.println("    [DB] Fetching Profile for " + id + "...");
        simulateNetworkLatency();
        System.out.println("    [DB] >> Profile data received.");
        return "User_Profile_Data";
    }

    /**
     * Simulates fetching a user's orders from the database.
     */
    public String getOrders(String id) {
        System.out.println("    [DB] Fetching Orders for " + id + "...");
        simulateNetworkLatency();
        System.out.println("    [DB] >> Order data received.");
        return "User_Orders_Data";
    }

    /**
     * Simulates fetching a user's activity from the database.
     */
    public String getActivity(String id) {
        System.out.println("    [DB] Fetching Activity for " + id + "...");
        simulateNetworkLatency();
        System.out.println("    [DB] >> Activity data received.");
        return "User_Activity_Data";
    }
}
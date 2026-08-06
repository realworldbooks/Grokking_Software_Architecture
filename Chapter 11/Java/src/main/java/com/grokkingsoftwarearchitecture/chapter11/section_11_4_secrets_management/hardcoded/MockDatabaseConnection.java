package com.grokkingsoftwarearchitecture.chapter11.section_11_4_secrets_management.hardcoded;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MOCK DATABASE CONNECTION - FOR DEMONSTRATION ONLY
 * 
 * This simulates a database connection to show the hardcoded credentials problem.
 * In reality, this would be a JDBC/PostgreSQL connection or similar.
 */
public class MockDatabaseConnection {
    private final String host;
    private final String database;
    private final String user;
    private final String password;
    private boolean isConnected;

    public MockDatabaseConnection(String host, String database, String user, String password) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.isConnected = false;
    }

    public boolean connect() {
        // Simulate connection logic
        if ("admin".equals(user) && "Password123!".equals(password)) {
            isConnected = true;
            return true;
        } else {
            throw new IllegalStateException("Authentication failed for user " + user);
        }
    }

    public List<Map<String, Object>> executeQuery(String query) {
        if (!isConnected) {
            throw new IllegalStateException("Not connected to database");
        }

        // Return mock data
        List<Map<String, Object>> orders = new ArrayList<>();
        Map<String, Object> order = new HashMap<>();
        order.put("order_id", "ORD-001");
        order.put("customer_id", "CUST-123");
        order.put("total", 99.99);
        orders.add(order);
        
        return orders;
    }

    public void close() {
        isConnected = false;
    }
}
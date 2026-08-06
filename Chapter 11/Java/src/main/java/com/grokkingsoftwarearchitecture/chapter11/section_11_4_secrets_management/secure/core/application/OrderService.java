package com.grokkingsoftwarearchitecture.chapter11.section_11_4_secrets_management.secure.core.application;

import com.grokkingsoftwarearchitecture.chapter11.section_11_4_secrets_management.secure.core.ports.SecretsProvider;
import com.grokkingsoftwarearchitecture.chapter11.section_11_4_secrets_management.secure.core.ports.SecretNotFoundException;

/**
 * APPLICATION SERVICE: Orchestrates order-related operations.
 * 
 * ARCHITECTURAL NOTE:
 * This is the Application Layer in Clean Architecture. It:
 * 1. Contains business logic
 * 2. Depends on abstractions (SecretsProvider), not concretions
 * 3. Doesn't know HOW secrets are retrieved, only THAT they can be retrieved
 * 
 * SECURITY BENEFITS:
 * - No credentials in source code
 * - Credentials rotated automatically by AWS
 * - Least privilege IAM role
 * - Full audit trail in CloudTrail
 */
public class OrderService {
    private final SecretsProvider secretsProvider;

    /**
     * Initialize the Order Service with a secrets provider.
     * 
     * @param secretsProvider Implementation of SecretsProvider (injected via DI)
     */
    public OrderService(SecretsProvider secretsProvider) {
        this.secretsProvider = secretsProvider;
    }

    /**
     * Connect to the database using credentials from the secrets manager.
     * 
     * @param secretName The name of the secret in AWS Secrets Manager
     * @return Result object with success boolean and message
     */
    public ConnectionResult connectToDatabase(String secretName) {
        try {
            // Retrieve credentials from the secrets manager
            // The application code only contains the SECRET NAME, not the secret itself!
            System.out.println("  [OrderService] Requesting credentials from secrets manager...");
            System.out.println("  [OrderService] Secret name: " + secretName);
            
            String[] credentials = secretsProvider.getDatabaseCredentials(secretName);
            String username = credentials[0];
            String password = credentials[1];
            
            System.out.println("  [OrderService] Credentials retrieved successfully");
            System.out.println("  [OrderService] Connecting to database with username: " + username);
            
            // In a real application, we would now use these credentials to connect
            // For demo purposes, we'll simulate a successful connection
            // String url = "jdbc:postgresql://prod-db.internal.castle.com:5432/orders_db";
            // Connection conn = DriverManager.getConnection(url, username, password);
            
            return new ConnectionResult(true, "Successfully connected to database as " + username);
            
        } catch (SecretNotFoundException ex) {
            String errorMsg = "Secret not found: " + ex.getMessage();
            System.out.println("  [OrderService] ERROR: " + errorMsg);
            return new ConnectionResult(false, errorMsg);
            
        } catch (SecurityException ex) {
            String errorMsg = "Permission denied: " + ex.getMessage();
            System.out.println("  [OrderService] ERROR: " + errorMsg);
            return new ConnectionResult(false, errorMsg);
            
        } catch (Exception ex) {
            String errorMsg = "Unexpected error: " + ex.getMessage();
            System.out.println("  [OrderService] ERROR: " + errorMsg);
            return new ConnectionResult(false, errorMsg);
        }
    }

    /**
     * Process an order by connecting to the database and updating the order.
     * 
     * @param orderId The order to process
     * @param secretName The secret name for database credentials
     * @return Status message
     */
    public String processOrder(String orderId, String secretName) {
        System.out.println("\n  [OrderService] Processing order " + orderId + "...");
        
        // Connect to database using secure credentials
        ConnectionResult result = connectToDatabase(secretName);
        
        if (!result.isSuccess()) {
            return "Failed to process order " + orderId + ": " + result.getMessage();
        }
        
        // Simulate order processing
        System.out.println("  [OrderService] Updating order status to 'PROCESSED'...");
        System.out.println("  [OrderService] Order " + orderId + " processed successfully!");
        
        return "Order " + orderId + " processed successfully";
    }

    /**
     * Simple result container for connection attempts.
     */
    public static class ConnectionResult {
        private final boolean success;
        private final String message;

        public ConnectionResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
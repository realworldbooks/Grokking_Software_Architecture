/**
 * APPLICATION LAYER - Order Service
 * 
 * This is the application service that orchestrates business logic.
 * It depends on the SecretsPort abstraction (Port), not on any
 * specific secrets manager implementation.
 * 
 * ARCHITECTURAL NOTE:
 * This is the Application Layer in Clean Architecture. It:
 * 1. Contains business logic
 * 2. Depends on abstractions (SecretsPort), not concretions
 * 3. Doesn't know HOW secrets are retrieved, only THAT they can be retrieved
 * 
 * SECURITY BENEFITS:
 * - No credentials in source code
 * - Credentials rotated automatically by AWS
 * - Least privilege IAM role
 * - Full audit trail in CloudTrail
 */

import { SecretNotFoundError, PermissionError } from '../domain/secretsPort.js';

export class OrderService {
    /**
     * Initialize the Order Service with a secrets provider.
     * 
     * @param {SecretsPort} secretsProvider - Implementation of SecretsPort (injected via DI)
     */
    constructor(secretsProvider) {
        this.secretsProvider = secretsProvider;
    }

    /**
     * Connect to the database using credentials from the secrets manager.
     * 
     * @param {string} secretName - The name of the secret in AWS Secrets Manager
     * @returns {Promise<{success: boolean, message: string}>} Result of connection attempt
     */
    async connectToDatabase(secretName) {
        try {
            // Retrieve credentials from the secrets manager
            // The application code only contains the SECRET NAME, not the secret itself!
            console.log("  [OrderService] Requesting credentials from secrets manager...");
            console.log(`  [OrderService] Secret name: ${secretName}`);
            
            const { username, password } = await this.secretsProvider.getDatabaseCredentials(secretName);
            
            console.log("  [OrderService] Credentials retrieved successfully");
            console.log(`  [OrderService] Connecting to database with username: ${username}`);
            
            // In a real application, we would now use these credentials to connect
            // For demo purposes, we'll simulate a successful connection
            // const { Client } = await import('pg');
            // const client = new Client({
            //     host: 'prod-db.internal.castle.com',
            //     database: 'orders_db',
            //     user: username,
            //     password: password
            // });
            
            return { success: true, message: `Successfully connected to database as ${username}` };
            
        } catch (error) {
            if (error instanceof SecretNotFoundError) {
                const errorMsg = `Secret not found: ${error.message}`;
                console.log(`  [OrderService] ERROR: ${errorMsg}`);
                return { success: false, message: errorMsg };
            } else if (error instanceof PermissionError) {
                const errorMsg = `Permission denied: ${error.message}`;
                console.log(`  [OrderService] ERROR: ${errorMsg}`);
                return { success: false, message: errorMsg };
            } else {
                const errorMsg = `Unexpected error: ${error.message}`;
                console.log(`  [OrderService] ERROR: ${errorMsg}`);
                return { success: false, message: errorMsg };
            }
        }
    }

    /**
     * Process an order by connecting to the database and updating the order.
     * 
     * @param {string} orderId - The order to process
     * @param {string} secretName - The secret name for database credentials
     * @returns {Promise<string>} Status message
     */
    async processOrder(orderId, secretName) {
        console.log(`\n  [OrderService] Processing order ${orderId}...`);
        
        // Connect to database using secure credentials
        const { success, message } = await this.connectToDatabase(secretName);
        
        if (!success) {
            return `Failed to process order ${orderId}: ${message}`;
        }
        
        // Simulate order processing
        console.log("  [OrderService] Updating order status to 'PROCESSED'...");
        console.log(`  [OrderService] Order ${orderId} processed successfully!`);
        
        return `Order ${orderId} processed successfully`;
    }
}
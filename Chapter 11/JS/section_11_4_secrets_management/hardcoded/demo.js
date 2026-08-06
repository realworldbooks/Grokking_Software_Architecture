import { MockDatabaseConnection } from './mockDatabase.js';

/**
 * THE HARDCODED DISASTER - Listing 11.1
 *
 * @description
 * This example demonstrates the WRONG way to handle secrets. We are showing
 * this anti-pattern to highlight why it's a catastrophic security risk.
 *
 * ARCHITECTURAL CRITIQUE:
 * 1. SOURCE CONTROL LEAKAGE: These credentials are now in git history forever.
 *    Anyone with repo access has the keys to the kingdom.
 *
 * 2. THE "GOD ACCOUNT" PROBLEM: Using the admin user violates the Principle of
 *    Least Privilege. If this service is breached, the attacker can drop every
 *    table in the database.
 *
 * 3. STATIC VULNERABILITY: If you need to change the password, you have to
 *    recompile, re-test, and re-deploy the entire application.
 */

export class Demo {
    
    static async run() {
        console.log("\n=== Section 11.4.1: The Hardcoded Disaster (Node.js) ===");
        console.log("THE SETUP: An Order Service that needs to connect to the Customer Database.");
        console.log("THE PROBLEM: Credentials are hardcoded directly in the source code.\n");

        // ---------------------------------------------------------
        // THE DISASTER UNFOLDS
        // ---------------------------------------------------------
        console.log("--- SCENARIO: Developer commits code with hardcoded credentials ---");
        console.log("  [Developer] Writing connection code...");
        console.log("  [Developer] 'I'll just put the password here for now...'");
        console.log("  [Developer] Commits to git and pushes to GitHub...\n");

        // This is the ANTI-PATTERN we're demonstrating
        // In a real scenario, this would be:
        // const { Client } = require('pg');
        // const client = new Client({
        //     host: 'prod-db.internal.castle.com',
        //     database: 'orders_db',
        //     user: 'admin',
        //     password: 'Password123!'
        // });
        
        console.log("  [Code Review] Missed! The credentials are buried in the implementation.");
        console.log("  [CI/CD] Pipeline builds and deploys successfully.");
        console.log("  [Production] Service is now running with hardcoded admin credentials.\n");

        // Attempt to use the hardcoded credentials
        console.log("--- Attempting database connection with hardcoded credentials ---");
        
        try {
            // THE DISASTER: Hardcoded credentials in source code
            const dbConnection = new MockDatabaseConnection(
                'prod-db.internal.castle.com',
                'orders_db',
                'admin',          // God mode account
                'Password123!'    // Hardcoded string - DISASTER!
            );
            
            const connectionSuccessful = dbConnection.connect();
            
            if (connectionSuccessful) {
                console.log("  [Result] Connection SUCCESSFUL (but at what cost?)");
                console.log("  [Data] Querying orders...");
                const orders = dbConnection.executeQuery('SELECT * FROM orders');
                console.log(`  [Data] Retrieved ${orders.length} orders`);
                dbConnection.close();
            }
        
        } catch (error) {
            console.log(`  [Result] Connection failed: ${error.message}`);
        }

        // ---------------------------------------------------------
        // THE ARCHITECTURAL VERDICT
        // ---------------------------------------------------------
        console.log("\n" + "=".repeat(70));
        console.log("ARCHITECTURAL VERDICT: CATASTROPHIC SECURITY FAILURE");
        console.log("-".repeat(70));
        console.log("VULNERABILITY #1: SOURCE CONTROL LEAKAGE");
        console.log("  - Credentials are now in git history forever");
        console.log("  - Even if deleted later, they remain in commit history");
        console.log("  - Anyone with read access to the repo has database access");
        console.log();
        console.log("VULNERABILITY #2: THE GOD ACCOUNT PROBLEM");
        console.log("  - Using 'admin' user violates Principle of Least Privilege");
        console.log("  - If service is breached, attacker can drop all tables");
        console.log("  - No audit trail of who did what with the admin account");
        console.log();
        console.log("VULNERABILITY #3: STATIC PASSWORD ROTATION");
        console.log("  - Changing password requires full code deployment");
        console.log("  - All environments (dev/staging/prod) use same password");
        console.log("  - No way to rotate without downtime");
        console.log();
        console.log("VULNERABILITY #4: NO AUDIT OR MONITORING");
        console.log("  - Cannot track who accessed the database");
        console.log("  - Cannot revoke access without code deployment");
        console.log("  - Credentials shared across all service instances");
        console.log();
        console.log("REALITY CHECK: This code will be committed, pushed, and deployed.");
        console.log("Those credentials are now compromised. The question is not IF they");
        console.log("will be leaked, but WHEN. This is an architectural disaster.");
        console.log("=".repeat(70) + "\n");
    }
}
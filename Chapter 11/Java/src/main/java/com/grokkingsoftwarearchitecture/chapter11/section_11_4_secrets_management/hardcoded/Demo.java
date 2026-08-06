package com.grokkingsoftwarearchitecture.chapter11.section_11_4_secrets_management.hardcoded;

/**
 * THE HARDCODED DISASTER - Listing 11.1
 *
 * DESIGN NOTE:
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
public class Demo {

    public static void run() {
        System.out.println("\n=== Section 11.4.1: The Hardcoded Disaster (Java) ===");
        System.out.println("THE SETUP: An Order Service that needs to connect to the Customer Database.");
        System.out.println("THE PROBLEM: Credentials are hardcoded directly in the source code.\n");

        // ---------------------------------------------------------
        // THE DISASTER UNFOLDS
        // ---------------------------------------------------------
        System.out.println("--- SCENARIO: Developer commits code with hardcoded credentials ---");
        System.out.println("  [Developer] Writing connection code...");
        System.out.println("  [Developer] 'I'll just put the password here for now...'");
        System.out.println("  [Developer] Commits to git and pushes to GitHub...\n");

        // This is the ANTI-PATTERN we're demonstrating
        // In a real scenario, this would be:
        // String url = "jdbc:postgresql://prod-db.internal.castle.com:5432/orders_db";
        // Connection conn = DriverManager.getConnection(url, "admin", "Password123!");
        
        System.out.println("  [Code Review] Missed! The credentials are buried in the implementation.");
        System.out.println("  [CI/CD] Pipeline builds and deploys successfully.");
        System.out.println("  [Production] Service is now running with hardcoded admin credentials.\n");

        // Attempt to use the hardcoded credentials
        System.out.println("--- Attempting database connection with hardcoded credentials ---");
        
        try {
            // THE DISASTER: Hardcoded credentials in source code
            MockDatabaseConnection dbConnection = new MockDatabaseConnection(
                "prod-db.internal.castle.com",
                "orders_db",
                "admin",           // God mode account
                "Password123!"     // Hardcoded string - DISASTER!
            );
            
            boolean connectionSuccessful = dbConnection.connect();
            
            if (connectionSuccessful) {
                System.out.println("  [Result] Connection SUCCESSFUL (but at what cost?)");
                System.out.println("  [Data] Querying orders...");
                var orders = dbConnection.executeQuery("SELECT * FROM orders");
                System.out.println("  [Data] Retrieved " + orders.size() + " orders");
                dbConnection.close();
            }
        } catch (Exception ex) {
            System.out.println("  [Result] Connection failed: " + ex.getMessage());
        }

        // ---------------------------------------------------------
        // THE ARCHITECTURAL VERDICT
        // ---------------------------------------------------------
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ARCHITECTURAL VERDICT: CATASTROPHIC SECURITY FAILURE");
        System.out.println("-".repeat(70));
        System.out.println("VULNERABILITY #1: SOURCE CONTROL LEAKAGE");
        System.out.println("  - Credentials are now in git history forever");
        System.out.println("  - Even if deleted later, they remain in commit history");
        System.out.println("  - Anyone with read access to the repo has database access");
        System.out.println();
        System.out.println("VULNERABILITY #2: THE GOD ACCOUNT PROBLEM");
        System.out.println("  - Using 'admin' user violates Principle of Least Privilege");
        System.out.println("  - If service is breached, attacker can drop all tables");
        System.out.println("  - No audit trail of who did what with the admin account");
        System.out.println();
        System.out.println("VULNERABILITY #3: STATIC PASSWORD ROTATION");
        System.out.println("  - Changing password requires full code deployment");
        System.out.println("  - All environments (dev/staging/prod) use same password");
        System.out.println("  - No way to rotate without downtime");
        System.out.println();
        System.out.println("VULNERABILITY #4: NO AUDIT OR MONITORING");
        System.out.println("  - Cannot track who accessed the database");
        System.out.println("  - Cannot revoke access without code deployment");
        System.out.println("  - Credentials shared across all service instances");
        System.out.println();
        System.out.println("REALITY CHECK: This code will be committed, pushed, and deployed.");
        System.out.println("Those credentials are now compromised. The question is not IF they");
        System.out.println("will be leaked, but WHEN. This is an architectural disaster.");
        System.out.println("=".repeat(70) + "\n");
    }
}
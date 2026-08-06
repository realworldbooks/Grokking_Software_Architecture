package com.grokkingsoftwarearchitecture.chapter11.section_11_4_secrets_management.secure;

import com.grokkingsoftwarearchitecture.chapter11.section_11_4_secrets_management.secure.core.application.OrderService;
import com.grokkingsoftwarearchitecture.chapter11.section_11_4_secrets_management.secure.infrastructure.AwsSecretsManagerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * SECURE RUNTIME RETRIEVAL - Listing 11.2
 *
 * DESIGN NOTE:
 * This example demonstrates the RIGHT way to handle secrets. We use AWS Secrets Manager
 * (mocked in-memory) to retrieve credentials at runtime. The application code contains
 * ONLY the secret name, never the actual credentials.
 *
 * ARCHITECTURAL BENEFITS:
 * 1. ZERO CREDENTIALS IN SOURCE CODE: The secret name is not sensitive, only the value is.
 * 2. IAM ROLE AUTHENTICATION: The compute platform's IAM role proves identity, no passwords needed.
 * 3. AUTOMATIC ROTATION: AWS rotates credentials automatically without code deployment.
 * 4. LEAST PRIVILEGE: The IAM role can only read its specific secret.
 * 5. FULL AUDIT TRAIL: All access logged in CloudTrail for compliance.
 */
public class Demo {

    public static void run() {
        System.out.println("\n=== Section 11.4.2: Secure Runtime Retrieval (Java) ===");
        System.out.println("THE SETUP: An Order Service that needs database credentials.");
        System.out.println("THE SOLUTION: Credentials retrieved from AWS Secrets Manager at runtime.\n");

        // ---------------------------------------------------------
        // SETUP: Create a mock secret in AWS Secrets Manager
        // ---------------------------------------------------------
        System.out.println("--- SETUP: Initializing AWS Secrets Manager (mocked) ---");
        
        try {
            // Create the AWS Secrets Manager adapter
            // In production, this would use the real AWS SDK with IAM role
            AwsSecretsManagerAdapter secretsAdapter = new AwsSecretsManagerAdapter("us-east-1");
            
            // Store a secret with database credentials
            // This simulates: aws secretsmanager create-secret --name ...
            String secretName = "prod/BillingDatabase/Credentials";
            
            Map<String, String> secretValue = new HashMap<>();
            secretValue.put("username", "order_service_user");
            secretValue.put("password", "SecureRandomPassword123!@#");
            secretValue.put("host", "prod-db.internal.castle.com");
            secretValue.put("database", "orders_db");
            
            secretsAdapter.seedSecret(secretName, secretValue);
            
            System.out.println("  [AWS Secrets Manager] Created secret: " + secretName);
            System.out.println("  [AWS Secrets Manager] Secret contains: username, password, host, database");
            System.out.println("  [AWS Secrets Manager] IAM Role: order-service-role (Least Privilege)\n");

            // ---------------------------------------------------------
            // THE SECURE ARCHITECTURE IN ACTION
            // ---------------------------------------------------------
            System.out.println("--- SCENARIO: Order Service starts and needs database credentials ---");
            System.out.println("  [EC2/ECS/Lambda] Instance starts with IAM role 'order-service-role'");
            System.out.println("  [OrderService] Application code contains ONLY the secret name");
            System.out.println("  [OrderService] Secret name: " + secretName);
            System.out.println("  [OrderService] No passwords, no credentials in source code!\n");

            // Create the Order Service with Dependency Injection
            // The service doesn't know it's using AWS - it just knows it has a secrets provider
            OrderService orderService = new OrderService(secretsAdapter);

            // ---------------------------------------------------------
            // EXECUTION: Process an order with secure credentials
            // ---------------------------------------------------------
            System.out.println("--- EXECUTING: Processing order with secure credential retrieval ---\n");
            
            // Process an order - credentials are fetched at runtime
            String result = orderService.processOrder("ORD-SECURE-001", secretName);
            
            System.out.println("\n  [Result] " + result);

            // ---------------------------------------------------------
            // DEMONSTRATE: What happens with wrong secret name
            // ---------------------------------------------------------
            System.out.println("\n--- SCENARIO: Attempting to access non-existent secret ---");
            System.out.println("  [OrderService] Trying to access wrong secret name...");
            
            String result2 = orderService.processOrder("ORD-SECURE-002", "prod/NonExistent/Credentials");
            
            System.out.println("\n  [Result] " + result2);

            // ---------------------------------------------------------
            // THE ARCHITECTURAL VERDICT
            // ---------------------------------------------------------
            System.out.println("\n" + "=".repeat(70));
            System.out.println("ARCHITECTURAL VERDICT: ZERO-TRUST SECURITY ARCHITECTURE");
            System.out.println("-".repeat(70));
            System.out.println("SECURITY #1: NO CREDENTIALS IN SOURCE CODE");
            System.out.println("  - Application code contains only the secret NAME (not sensitive)");
            System.out.println("  - Actual credentials stored securely in AWS Secrets Manager");
            System.out.println("  - Git repository is clean - no secrets to leak");
            System.out.println();
            System.out.println("SECURITY #2: IAM ROLE AUTHENTICATION");
            System.out.println("  - Compute platform (EC2/ECS/Lambda) has IAM role assigned");
            System.out.println("  - Application proves identity via IAM role, not passwords");
            System.out.println("  - No permanent access keys needed");
            System.out.println();
            System.out.println("SECURITY #3: AUTOMATIC CREDENTIAL ROTATION");
            System.out.println("  - AWS rotates credentials automatically (no code deployment)");
            System.out.println("  - Short-lived credentials reduce blast radius if compromised");
            System.out.println("  - Rotation schedule configurable per secret");
            System.out.println();
            System.out.println("SECURITY #4: LEAST PRIVILEGE ACCESS");
            System.out.println("  - IAM role can only read its specific secret");
            System.out.println("  - Cannot access other secrets or AWS resources");
            System.out.println("  - Principle of Least Privilege enforced by IAM policies");
            System.out.println();
            System.out.println("SECURITY #5: FULL AUDIT TRAIL");
            System.out.println("  - All secret access logged in AWS CloudTrail");
            System.out.println("  - Compliance reporting available");
            System.out.println("  - Can detect and alert on suspicious access patterns");
            System.out.println();
            System.out.println("OPERATIONAL BENEFITS:");
            System.out.println("  - Rotate passwords without downtime or deployment");
            System.out.println("  - Different credentials per environment (dev/staging/prod)");
            System.out.println("  - Centralized secrets management across all services");
            System.out.println("  - Integration with AWS IAM for fine-grained access control");
            System.out.println();
            System.out.println("REALITY CHECK: This is how modern cloud-native applications");
            System.out.println("handle secrets. The application never sees the actual password,");
            System.out.println("only the secret name. The cloud provider handles authentication,");
            System.out.println("rotation, and audit logging. Humans and permanent credentials");
            System.out.println("are completely removed from the equation.");
            System.out.println("=".repeat(70) + "\n");
        } catch (Exception e) {
            System.out.println("[ERROR] Execution failed: " + e.getMessage());
        }
    }
}
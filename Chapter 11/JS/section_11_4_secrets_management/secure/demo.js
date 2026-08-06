import { OrderService } from './application/orderService.js';
import { AwsSecretsManagerAdapter } from './infrastructure/awsSecretsAdapter.js';

/**
 * SECURE RUNTIME RETRIEVAL - Listing 11.2
 *
 * @description
 * This example demonstrates the RIGHT way to handle secrets. We use AWS Secrets Manager
 * to retrieve credentials at runtime. The application code contains ONLY the secret name,
 * never the actual credentials.
 *
 * ARCHITECTURAL BENEFITS:
 * 1. ZERO CREDENTIALS IN SOURCE CODE: The secret name is not sensitive, only the value is.
 * 2. IAM ROLE AUTHENTICATION: The compute platform's IAM role proves identity, no passwords needed.
 * 3. AUTOMATIC ROTATION: AWS rotates credentials automatically without code deployment.
 * 4. LEAST PRIVILEGE: The IAM role can only read its specific secret.
 * 5. FULL AUDIT TRAIL: All access logged in CloudTrail for compliance.
 */

export class Demo {
    
    static async run() {
        console.log("\n=== Section 11.4.2: Secure Runtime Retrieval (Node.js) ===");
        console.log("THE SETUP: An Order Service that needs database credentials.");
        console.log("THE SOLUTION: Credentials retrieved from AWS Secrets Manager at runtime.\n");

        // ---------------------------------------------------------
        // SETUP: Create a mock secret in AWS Secrets Manager
        // ---------------------------------------------------------
        console.log("--- SETUP: Initializing AWS Secrets Manager (mocked) ---");
        
        // Create the AWS Secrets Manager adapter
        // In production, this would use the real AWS SDK with IAM role
        const secretsAdapter = new AwsSecretsManagerAdapter({ region: 'us-east-1' });
        
        // Store a secret with database credentials
        // This simulates: aws secretsmanager create-secret --name ...
        const secretName = "prod/BillingDatabase/Credentials";
        secretsAdapter.seedSecret(secretName, {
            username: "order_service_user",
            password: "SecureRandomPassword123!@#",
            host: "prod-db.internal.castle.com",
            database: "orders_db"
        });
        
        console.log(`  [AWS Secrets Manager] Created secret: ${secretName}`);
        console.log("  [AWS Secrets Manager] Secret contains: username, password, host, database");
        console.log("  [AWS Secrets Manager] IAM Role: order-service-role (Least Privilege)\n");

        // ---------------------------------------------------------
        // THE SECURE ARCHITECTURE IN ACTION
        // ---------------------------------------------------------
        console.log("--- SCENARIO: Order Service starts and needs database credentials ---");
        console.log("  [EC2/ECS/Lambda] Instance starts with IAM role 'order-service-role'");
        console.log("  [OrderService] Application code contains ONLY the secret name");
        console.log(`  [OrderService] Secret name: ${secretName}`);
        console.log("  [OrderService] No passwords, no credentials in source code!\n");

        // Create the Order Service with Dependency Injection
        // The service doesn't know it's using AWS - it just knows it has a secrets provider
        const orderService = new OrderService(secretsAdapter);

        // ---------------------------------------------------------
        // EXECUTION: Process an order with secure credentials
        // ---------------------------------------------------------
        console.log("--- EXECUTING: Processing order with secure credential retrieval ---\n");
        
        // Process an order - credentials are fetched at runtime
        const result = await orderService.processOrder(
            "ORD-SECURE-001",
            secretName
        );
        
        console.log(`\n  [Result] ${result}`);

        // ---------------------------------------------------------
        // DEMONSTRATE: What happens with wrong secret name
        // ---------------------------------------------------------
        console.log("\n--- SCENARIO: Attempting to access non-existent secret ---");
        console.log("  [OrderService] Trying to access wrong secret name...");
        
        const result2 = await orderService.processOrder(
            "ORD-SECURE-002",
            "prod/NonExistent/Credentials"
        );
        
        console.log(`\n  [Result] ${result2}`);

        // ---------------------------------------------------------
        // THE ARCHITECTURAL VERDICT
        // ---------------------------------------------------------
        console.log("\n" + "=".repeat(70));
        console.log("ARCHITECTURAL VERDICT: ZERO-TRUST SECURITY ARCHITECTURE");
        console.log("-".repeat(70));
        console.log("SECURITY #1: NO CREDENTIALS IN SOURCE CODE");
        console.log("  - Application code contains only the secret NAME (not sensitive)");
        console.log("  - Actual credentials stored securely in AWS Secrets Manager");
        console.log("  - Git repository is clean - no secrets to leak");
        console.log();
        console.log("SECURITY #2: IAM ROLE AUTHENTICATION");
        console.log("  - Compute platform (EC2/ECS/Lambda) has IAM role assigned");
        console.log("  - Application proves identity via IAM role, not passwords");
        console.log("  - No permanent access keys needed");
        console.log();
        console.log("SECURITY #3: AUTOMATIC CREDENTIAL ROTATION");
        console.log("  - AWS rotates credentials automatically (no code deployment)");
        console.log("  - Short-lived credentials reduce blast radius if compromised");
        console.log("  - Rotation schedule configurable per secret");
        console.log();
        console.log("SECURITY #4: LEAST PRIVILEGE ACCESS");
        console.log("  - IAM role can only read its specific secret");
        console.log("  - Cannot access other secrets or AWS resources");
        console.log("  - Principle of Least Privilege enforced by IAM policies");
        console.log();
        console.log("SECURITY #5: FULL AUDIT TRAIL");
        console.log("  - All secret access logged in AWS CloudTrail");
        console.log("  - Compliance reporting available");
        console.log("  - Can detect and alert on suspicious access patterns");
        console.log();
        console.log("OPERATIONAL BENEFITS:");
        console.log("  - Rotate passwords without downtime or deployment");
        console.log("  - Different credentials per environment (dev/staging/prod)");
        console.log("  - Centralized secrets management across all services");
        console.log("  - Integration with AWS IAM for fine-grained access control");
        console.log();
        console.log("REALITY CHECK: This is how modern cloud-native applications");
        console.log("handle secrets. The application never sees the actual password,");
        console.log("only the secret name. The cloud provider handles authentication,");
        console.log("rotation, and audit logging. Humans and permanent credentials");
        console.log("are completely removed from the equation.");
        console.log("=".repeat(70) + "\n");
    }
}
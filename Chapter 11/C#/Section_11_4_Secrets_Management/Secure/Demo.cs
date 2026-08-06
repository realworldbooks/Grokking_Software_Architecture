using Chapter11.Secure.Core.Application;
using Chapter11.Secure.Infrastructure;

namespace Chapter11.Secure;

/// <summary>
/// SECURE RUNTIME RETRIEVAL - Listing 11.2
///
/// DESIGN NOTE:
/// This example demonstrates the RIGHT way to handle secrets. We use AWS Secrets Manager
/// (mocked in-memory) to retrieve credentials at runtime. The application code contains
/// ONLY the secret name, never the actual credentials.
///
/// ARCHITECTURAL BENEFITS:
/// 1. ZERO CREDENTIALS IN SOURCE CODE: The secret name is not sensitive, only the value is.
/// 2. IAM ROLE AUTHENTICATION: The compute platform's IAM role proves identity, no passwords needed.
/// 3. AUTOMATIC ROTATION: AWS rotates credentials automatically without code deployment.
/// 4. LEAST PRIVILEGE: The IAM role can only read its specific secret.
/// 5. FULL AUDIT TRAIL: All access logged in CloudTrail for compliance.
/// </summary>
public static class Demo
{
    public static async Task Run()
    {
        Console.WriteLine("\n=== Section 11.4.2: Secure Runtime Retrieval (C#) ===");
        Console.WriteLine("THE SETUP: An Order Service that needs database credentials.");
        Console.WriteLine("THE SOLUTION: Credentials retrieved from AWS Secrets Manager at runtime.\n");

        // ---------------------------------------------------------
        // SETUP: Create a mock secret in AWS Secrets Manager
        // ---------------------------------------------------------
        Console.WriteLine("--- SETUP: Initializing AWS Secrets Manager (mocked) ---");
        
        // Create the AWS Secrets Manager adapter
        // In production, this would use the real AWS SDK with IAM role
        var secretsAdapter = new AwsSecretsManagerAdapter("us-east-1");
        
        // Store a secret with database credentials
        // This simulates: aws secretsmanager create-secret --name ...
        var secretName = "prod/BillingDatabase/Credentials";
        secretsAdapter.SeedSecret(secretName, new
        {
            username = "order_service_user",
            password = "SecureRandomPassword123!@#",
            host = "prod-db.internal.castle.com",
            database = "orders_db"
        });
        
        Console.WriteLine($"  [AWS Secrets Manager] Created secret: {secretName}");
        Console.WriteLine("  [AWS Secrets Manager] Secret contains: username, password, host, database");
        Console.WriteLine("  [AWS Secrets Manager] IAM Role: order-service-role (Least Privilege)\n");

        // ---------------------------------------------------------
        // THE SECURE ARCHITECTURE IN ACTION
        // ---------------------------------------------------------
        Console.WriteLine("--- SCENARIO: Order Service starts and needs database credentials ---");
        Console.WriteLine("  [EC2/ECS/Lambda] Instance starts with IAM role 'order-service-role'");
        Console.WriteLine("  [OrderService] Application code contains ONLY the secret name");
        Console.WriteLine($"  [OrderService] Secret name: {secretName}");
        Console.WriteLine("  [OrderService] No passwords, no credentials in source code!\n");

        // Create the Order Service with Dependency Injection
        // The service doesn't know it's using AWS - it just knows it has a secrets provider
        var orderService = new OrderService(secretsAdapter);

        // ---------------------------------------------------------
        // EXECUTION: Process an order with secure credentials
        // ---------------------------------------------------------
        Console.WriteLine("--- EXECUTING: Processing order with secure credential retrieval ---\n");
        
        // Process an order - credentials are fetched at runtime
        var result = await orderService.ProcessOrderAsync("ORD-SECURE-001", secretName);
        
        Console.WriteLine($"\n  [Result] {result}");

        // ---------------------------------------------------------
        // DEMONSTRATE: What happens with wrong secret name
        // ---------------------------------------------------------
        Console.WriteLine("\n--- SCENARIO: Attempting to access non-existent secret ---");
        Console.WriteLine("  [OrderService] Trying to access wrong secret name...");
        
        var result2 = await orderService.ProcessOrderAsync("ORD-SECURE-002", "prod/NonExistent/Credentials");
        
        Console.WriteLine($"\n  [Result] {result2}");

        // ---------------------------------------------------------
        // THE ARCHITECTURAL VERDICT
        // ---------------------------------------------------------
        Console.WriteLine("\n" + new string('=', 70));
        Console.WriteLine("ARCHITECTURAL VERDICT: ZERO-TRUST SECURITY ARCHITECTURE");
        Console.WriteLine(new string('-', 70));
        Console.WriteLine("SECURITY #1: NO CREDENTIALS IN SOURCE CODE");
        Console.WriteLine("  - Application code contains only the secret NAME (not sensitive)");
        Console.WriteLine("  - Actual credentials stored securely in AWS Secrets Manager");
        Console.WriteLine("  - Git repository is clean - no secrets to leak");
        Console.WriteLine();
        Console.WriteLine("SECURITY #2: IAM ROLE AUTHENTICATION");
        Console.WriteLine("  - Compute platform (EC2/ECS/Lambda) has IAM role assigned");
        Console.WriteLine("  - Application proves identity via IAM role, not passwords");
        Console.WriteLine("  - No permanent access keys needed");
        Console.WriteLine();
        Console.WriteLine("SECURITY #3: AUTOMATIC CREDENTIAL ROTATION");
        Console.WriteLine("  - AWS rotates credentials automatically (no code deployment)");
        Console.WriteLine("  - Short-lived credentials reduce blast radius if compromised");
        Console.WriteLine("  - Rotation schedule configurable per secret");
        Console.WriteLine();
        Console.WriteLine("SECURITY #4: LEAST PRIVILEGE ACCESS");
        Console.WriteLine("  - IAM role can only read its specific secret");
        Console.WriteLine("  - Cannot access other secrets or AWS resources");
        Console.WriteLine("  - Principle of Least Privilege enforced by IAM policies");
        Console.WriteLine();
        Console.WriteLine("SECURITY #5: FULL AUDIT TRAIL");
        Console.WriteLine("  - All secret access logged in AWS CloudTrail");
        Console.WriteLine("  - Compliance reporting available");
        Console.WriteLine("  - Can detect and alert on suspicious access patterns");
        Console.WriteLine();
        Console.WriteLine("OPERATIONAL BENEFITS:");
        Console.WriteLine("  - Rotate passwords without downtime or deployment");
        Console.WriteLine("  - Different credentials per environment (dev/staging/prod)");
        Console.WriteLine("  - Centralized secrets management across all services");
        Console.WriteLine("  - Integration with AWS IAM for fine-grained access control");
        Console.WriteLine();
        Console.WriteLine("REALITY CHECK: This is how modern cloud-native applications");
        Console.WriteLine("handle secrets. The application never sees the actual password,");
        Console.WriteLine("only the secret name. The cloud provider handles authentication,");
        Console.WriteLine("rotation, and audit logging. Humans and permanent credentials");
        Console.WriteLine("are completely removed from the equation.");
        Console.WriteLine(new string('=', 70) + "\n");
    }
}
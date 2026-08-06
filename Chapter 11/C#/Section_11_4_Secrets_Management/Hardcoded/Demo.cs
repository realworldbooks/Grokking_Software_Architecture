namespace Chapter11.Hardcoded;

/// <summary>
/// THE HARDCODED DISASTER - Listing 11.1
///
/// DESIGN NOTE:
/// This example demonstrates the WRONG way to handle secrets. We are showing
/// this anti-pattern to highlight why it's a catastrophic security risk.
///
/// ARCHITECTURAL CRITIQUE:
/// 1. SOURCE CONTROL LEAKAGE: These credentials are now in git history forever.
///    Anyone with repo access has the keys to the kingdom.
///
/// 2. THE "GOD ACCOUNT" PROBLEM: Using the admin user violates the Principle of
///    Least Privilege. If this service is breached, the attacker can drop every
///    table in the database.
///
/// 3. STATIC VULNERABILITY: If you need to change the password, you have to
///    recompile, re-test, and re-deploy the entire application.
/// </summary>
public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("\n=== Section 11.4.1: The Hardcoded Disaster (C#) ===");
        Console.WriteLine("THE SETUP: An Order Service that needs to connect to the Customer Database.");
        Console.WriteLine("THE PROBLEM: Credentials are hardcoded directly in the source code.\n");

        // ---------------------------------------------------------
        // THE DISASTER UNFOLDS
        // ---------------------------------------------------------
        Console.WriteLine("--- SCENARIO: Developer commits code with hardcoded credentials ---");
        Console.WriteLine("  [Developer] Writing connection code...");
        Console.WriteLine("  [Developer] 'I'll just put the password here for now...'");
        Console.WriteLine("  [Developer] Commits to git and pushes to GitHub...\n");

        // This is the ANTI-PATTERN we're demonstrating
        // In a real scenario, this would be:
        // var conn = new NpgsqlConnection(
        //     "Host=prod-db.internal.castle.com;Database=orders_db;Username=admin;Password=Password123!"
        // );
        
        Console.WriteLine("  [Code Review] Missed! The credentials are buried in the implementation.");
        Console.WriteLine("  [CI/CD] Pipeline builds and deploys successfully.");
        Console.WriteLine("  [Production] Service is now running with hardcoded admin credentials.\n");

        // Attempt to use the hardcoded credentials
        Console.WriteLine("--- Attempting database connection with hardcoded credentials ---");
        
        try
        {
            // THE DISASTER: Hardcoded credentials in source code
            var dbConnection = new MockDatabaseConnection(
                host: "prod-db.internal.castle.com",
                database: "orders_db",
                user: "admin",          // God mode account
                password: "Password123!" // Hardcoded string - DISASTER!
            );
            
            var connectionSuccessful = dbConnection.Connect();
            
            if (connectionSuccessful)
            {
                Console.WriteLine("  [Result] Connection SUCCESSFUL (but at what cost?)");
                Console.WriteLine("  [Data] Querying orders...");
                var orders = dbConnection.ExecuteQuery("SELECT * FROM orders");
                Console.WriteLine($"  [Data] Retrieved {orders.Count} orders");
                dbConnection.Close();
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"  [Result] Connection failed: {ex.Message}");
        }

        // ---------------------------------------------------------
        // THE ARCHITECTURAL VERDICT
        // ---------------------------------------------------------
        Console.WriteLine("\n" + new string('=', 70));
        Console.WriteLine("ARCHITECTURAL VERDICT: CATASTROPHIC SECURITY FAILURE");
        Console.WriteLine(new string('-', 70));
        Console.WriteLine("VULNERABILITY #1: SOURCE CONTROL LEAKAGE");
        Console.WriteLine("  - Credentials are now in git history forever");
        Console.WriteLine("  - Even if deleted later, they remain in commit history");
        Console.WriteLine("  - Anyone with read access to the repo has database access");
        Console.WriteLine();
        Console.WriteLine("VULNERABILITY #2: THE GOD ACCOUNT PROBLEM");
        Console.WriteLine("  - Using 'admin' user violates Principle of Least Privilege");
        Console.WriteLine("  - If service is breached, attacker can drop all tables");
        Console.WriteLine("  - No audit trail of who did what with the admin account");
        Console.WriteLine();
        Console.WriteLine("VULNERABILITY #3: STATIC PASSWORD ROTATION");
        Console.WriteLine("  - Changing password requires full code deployment");
        Console.WriteLine("  - All environments (dev/staging/prod) use same password");
        Console.WriteLine("  - No way to rotate without downtime");
        Console.WriteLine();
        Console.WriteLine("VULNERABILITY #4: NO AUDIT OR MONITORING");
        Console.WriteLine("  - Cannot track who accessed the database");
        Console.WriteLine("  - Cannot revoke access without code deployment");
        Console.WriteLine("  - Credentials shared across all service instances");
        Console.WriteLine();
        Console.WriteLine("REALITY CHECK: This code will be committed, pushed, and deployed.");
        Console.WriteLine("Those credentials are now compromised. The question is not IF they");
        Console.WriteLine("will be leaked, but WHEN. This is an architectural disaster.");
        Console.WriteLine(new string('=', 70) + "\n");
    }
}
using Chapter11.Secure.Core.Ports;

namespace Chapter11.Secure.Core.Application;

/// <summary>
/// APPLICATION SERVICE: Orchestrates order-related operations.
/// 
/// ARCHITECTURAL NOTE:
/// This is the Application Layer in Clean Architecture. It:
/// 1. Contains business logic
/// 2. Depends on abstractions (ISecretsProvider), not concretions
/// 3. Doesn't know HOW secrets are retrieved, only THAT they can be retrieved
/// 
/// SECURITY BENEFITS:
/// - No credentials in source code
/// - Credentials rotated automatically by AWS
/// - Least privilege IAM role
/// - Full audit trail in CloudTrail
/// </summary>
public class OrderService
{
    private readonly ISecretsProvider _secretsProvider;

    /// <summary>
    /// Initialize the Order Service with a secrets provider.
    /// </summary>
    /// <param name="secretsProvider">Implementation of ISecretsProvider (injected via DI)</param>
    public OrderService(ISecretsProvider secretsProvider)
    {
        _secretsProvider = secretsProvider;
    }

    /// <summary>
    /// Connect to the database using credentials from the secrets manager.
    /// </summary>
    /// <param name="secretName">The name of the secret in AWS Secrets Manager</param>
    /// <returns>Tuple of (success, message)</returns>
    public async Task<(bool Success, string Message)> ConnectToDatabaseAsync(string secretName)
    {
        try
        {
            // Retrieve credentials from the secrets manager
            // The application code only contains the SECRET NAME, not the secret itself!
            Console.WriteLine("  [OrderService] Requesting credentials from secrets manager...");
            Console.WriteLine($"  [OrderService] Secret name: {secretName}");
            
            var (username, password) = await _secretsProvider.GetDatabaseCredentialsAsync(secretName);
            
            Console.WriteLine("  [OrderService] Credentials retrieved successfully");
            Console.WriteLine($"  [OrderService] Connecting to database with username: {username}");
            
            // In a real application, we would now use these credentials to connect
            // For demo purposes, we'll simulate a successful connection
            // var conn = new NpgsqlConnection(
            //     $"Host=prod-db.internal.castle.com;Database=orders_db;Username={username};Password={password}"
            // );
            
            return (true, $"Successfully connected to database as {username}");
        }
        catch (SecretNotFoundException ex)
        {
            var errorMsg = $"Secret not found: {ex.Message}";
            Console.WriteLine($"  [OrderService] ERROR: {errorMsg}");
            return (false, errorMsg);
        }
        catch (UnauthorizedAccessException ex)
        {
            var errorMsg = $"Permission denied: {ex.Message}";
            Console.WriteLine($"  [OrderService] ERROR: {errorMsg}");
            return (false, errorMsg);
        }
        catch (Exception ex)
        {
            var errorMsg = $"Unexpected error: {ex.Message}";
            Console.WriteLine($"  [OrderService] ERROR: {errorMsg}");
            return (false, errorMsg);
        }
    }

    /// <summary>
    /// Process an order by connecting to the database and updating the order.
    /// </summary>
    /// <param name="orderId">The order to process</param>
    /// <param name="secretName">The secret name for database credentials</param>
    /// <returns>Status message</returns>
    public async Task<string> ProcessOrderAsync(string orderId, string secretName)
    {
        Console.WriteLine($"\n  [OrderService] Processing order {orderId}...");
        
        // Connect to database using secure credentials
        var (success, message) = await ConnectToDatabaseAsync(secretName);
        
        if (!success)
        {
            return $"Failed to process order {orderId}: {message}";
        }
        
        // Simulate order processing
        Console.WriteLine("  [OrderService] Updating order status to 'PROCESSED'...");
        Console.WriteLine($"  [OrderService] Order {orderId} processed successfully!");
        
        return $"Order {orderId} processed successfully";
    }
}
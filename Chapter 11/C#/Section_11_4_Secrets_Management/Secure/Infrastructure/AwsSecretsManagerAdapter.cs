using System.Collections.Concurrent;
using System.Text.Json;
using Chapter11.Secure.Core.Ports;

namespace Chapter11.Secure.Infrastructure;

/// <summary>
/// ADAPTER: AWS Secrets Manager implementation of ISecretsProvider.
/// 
/// ARCHITECTURAL NOTE:
/// This is the Infrastructure Adapter that implements the Domain Port.
/// It knows HOW to retrieve secrets from AWS, but the application layer
/// only knows THAT it can retrieve secrets (via the interface).
/// 
/// SECURITY FEATURES:
/// 1. No hardcoded credentials - uses IAM role from compute platform
/// 2. Short-lived credentials automatically rotated by AWS
/// 3. Least privilege - only reads the specific secret needed
/// 4. Audit trail - all access logged in CloudTrail
/// </summary>
public class AwsSecretsManagerAdapter : ISecretsProvider
{
    private readonly string _region;
    
    // For demo purposes, we simulate the AWS Secrets Manager service
    // In production, this would use:
    // var client = new AmazonSecretsManagerClient(RegionEndpoint.GetBySystemName(region));
    private readonly ConcurrentDictionary<string, string> _mockSecrets;

    /// <summary>
    /// Initialize the AWS Secrets Manager adapter.
    /// </summary>
    /// <param name="region">AWS region (default: us-east-1)</param>
    public AwsSecretsManagerAdapter(string region = "us-east-1")
    {
        _region = region;
        _mockSecrets = new ConcurrentDictionary<string, string>();
    }

    /// <summary>
    /// Seed a secret into the mock store (simulates AWS console/CLI creating a secret).
    /// </summary>
    public void SeedSecret(string secretName, object secretValue)
    {
        _mockSecrets[secretName] = JsonSerializer.Serialize(secretValue);
    }

    /// <summary>
    /// Retrieve database credentials from AWS Secrets Manager.
    /// </summary>
    /// <param name="secretName">The name/ARN of the secret (e.g., "prod/BillingDatabase/Credentials")</param>
    /// <returns>Tuple of (username, password)</returns>
    /// <exception cref="SecretNotFoundException">If the secret doesn't exist</exception>
    /// <exception cref="UnauthorizedAccessException">If the IAM role lacks permission</exception>
    public Task<(string username, string password)> GetDatabaseCredentialsAsync(string secretName)
    {
        // In production, this would be:
        // var request = new GetSecretValueRequest { SecretId = secretName };
        // var response = await client.GetSecretValueAsync(request);
        // var secret = JsonSerializer.Deserialize<Dictionary<string, string>>(response.SecretString);
        
        // Simulate IAM permission check
        if (!_mockSecrets.TryGetValue(secretName, out var secretString))
        {
            throw new SecretNotFoundException($"Secret '{secretName}' not found");
        }
        
        var secret = JsonSerializer.Deserialize<Dictionary<string, string>>(secretString);
        
        return Task.FromResult((secret!["username"], secret["password"]));
    }
}
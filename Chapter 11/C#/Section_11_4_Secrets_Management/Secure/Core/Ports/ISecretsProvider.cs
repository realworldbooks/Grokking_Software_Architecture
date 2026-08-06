namespace Chapter11.Secure.Core.Ports;

/// <summary>
/// PORT: Abstract interface for secrets management.
/// 
/// ARCHITECTURAL NOTE:
/// This is a Port in Hexagonal Architecture. The application core defines
/// WHAT it needs (an interface), and the infrastructure layer provides
/// HOW it's implemented (AWS Secrets Manager, HashiCorp Vault, etc.).
/// 
/// This allows us to:
/// 1. Swap secrets providers without changing business logic
/// 2. Test with mock implementations
/// 3. Support multiple cloud providers
/// </summary>
public interface ISecretsProvider
{
    /// <summary>
    /// Retrieve database credentials from the secrets manager.
    /// </summary>
    /// <param name="secretName">The name/ARN of the secret to retrieve</param>
    /// <returns>Tuple of (username, password)</returns>
    /// <exception cref="SecretNotFoundException">If the secret doesn't exist</exception>
    /// <exception cref="UnauthorizedAccessException">If the IAM role lacks permission to access the secret</exception>
    Task<(string username, string password)> GetDatabaseCredentialsAsync(string secretName);
}

/// <summary>
/// Raised when a requested secret cannot be found.
/// </summary>
public class SecretNotFoundException : Exception
{
    public SecretNotFoundException(string message) : base(message) { }
}
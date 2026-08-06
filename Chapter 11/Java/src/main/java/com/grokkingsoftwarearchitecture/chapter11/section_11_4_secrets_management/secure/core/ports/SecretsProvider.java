package com.grokkingsoftwarearchitecture.chapter11.section_11_4_secrets_management.secure.core.ports;

/**
 * PORT: Abstract interface for secrets management.
 * 
 * ARCHITECTURAL NOTE:
 * This is a Port in Hexagonal Architecture. The application core defines
 * WHAT it needs (an interface), and the infrastructure layer provides
 * HOW it's implemented (AWS Secrets Manager, HashiCorp Vault, etc.).
 * 
 * This allows us to:
 * 1. Swap secrets providers without changing business logic
 * 2. Test with mock implementations
 * 3. Support multiple cloud providers
 */
public interface SecretsProvider {
    /**
     * Retrieve database credentials from the secrets manager.
     * 
     * @param secretName The name/ARN of the secret to retrieve
     * @return Array of [username, password]
     * @throws SecretNotFoundException If the secret doesn't exist
     * @throws SecurityException If the IAM role lacks permission to access the secret
     */
    String[] getDatabaseCredentials(String secretName);
}
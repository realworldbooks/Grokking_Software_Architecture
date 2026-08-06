package com.grokkingsoftwarearchitecture.chapter11.section_11_4_secrets_management.secure.infrastructure;

import com.grokkingsoftwarearchitecture.chapter11.section_11_4_secrets_management.secure.core.ports.SecretsProvider;
import com.grokkingsoftwarearchitecture.chapter11.section_11_4_secrets_management.secure.core.ports.SecretNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ADAPTER: AWS Secrets Manager implementation of SecretsProvider.
 * 
 * ARCHITECTURAL NOTE:
 * This is the Infrastructure Adapter that implements the Domain Port.
 * It knows HOW to retrieve secrets from AWS, but the application layer
 * only knows THAT it can retrieve secrets (via the interface).
 * 
 * SECURITY FEATURES:
 * 1. No hardcoded credentials - uses IAM role from compute platform
 * 2. Short-lived credentials automatically rotated by AWS
 * 3. Least privilege - only reads the specific secret needed
 * 4. Audit trail - all access logged in CloudTrail
 */
public class AwsSecretsManagerAdapter implements SecretsProvider {
    private final String region;
    
    // For demo purposes, we simulate the AWS Secrets Manager service
    // In production, this would use:
    // var client = SecretsManagerClient.builder()
    //         .region(Region.of(region))
    //         .build();
    private final Map<String, String> mockSecrets;
    private final ObjectMapper objectMapper;

    /**
     * Initialize the AWS Secrets Manager adapter.
     * 
     * @param region AWS region (default: us-east-1)
     */
    public AwsSecretsManagerAdapter(String region) {
        this.region = region;
        this.mockSecrets = new ConcurrentHashMap<>();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Seed a secret into the mock store (simulates AWS console/CLI creating a secret).
     * 
     * @param secretName The secret name
     * @param secretValue The secret value object
     * @throws Exception If JSON serialization fails
     */
    public void seedSecret(String secretName, Object secretValue) throws Exception {
        mockSecrets.put(secretName, objectMapper.writeValueAsString(secretValue));
    }

    /**
     * Retrieve database credentials from AWS Secrets Manager.
     * 
     * @param secretName The name/ARN of the secret (e.g., "prod/BillingDatabase/Credentials")
     * @return Array of [username, password]
     * @throws SecretNotFoundException If the secret doesn't exist
     * @throws SecurityException If the IAM role lacks permission
     */
    @Override
    public String[] getDatabaseCredentials(String secretName) {
        // In production, this would be:
        // var request = GetSecretValueRequest.builder().secretId(secretName).build();
        // var response = client.getSecretValue(request);
        // Map<String, String> secret = objectMapper.readValue(response.secretString(), ...);
        
        // Simulate IAM permission check
        if (!mockSecrets.containsKey(secretName)) {
            throw new SecretNotFoundException("Secret '" + secretName + "' not found");
        }
        
        try {
            String secretString = mockSecrets.get(secretName);
            Map<String, String> secret = objectMapper.readValue(secretString, new TypeReference<Map<String, String>>() {});
            
            return new String[] { secret.get("username"), secret.get("password") };
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse secret value", e);
        }
    }
}
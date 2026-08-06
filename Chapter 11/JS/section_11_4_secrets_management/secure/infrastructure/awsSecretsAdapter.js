/**
 * INFRASTRUCTURE LAYER - AWS Secrets Manager Adapter
 * 
 * This adapter implements the SecretsPort interface using AWS Secrets Manager.
 * In production, this would connect to the real AWS service. For this demo,
 * we use a local in-memory mock to simulate AWS so the code is fully runnable
 * without AWS credentials.
 */

import { SecretsPort, SecretNotFoundError, PermissionError } from '../domain/secretsPort.js';

/**
 * ADAPTER: AWS Secrets Manager implementation of SecretsPort.
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
export class AwsSecretsManagerAdapter extends SecretsPort {
    /**
     * @param {Object} options - Configuration options
     * @param {string} options.region - AWS region (default: us-east-1)
     * @param {Map<string, Object>} [options.mockSecrets] - Optional in-memory secret store for demos
     */
    constructor({ region = 'us-east-1', mockSecrets = null } = {}) {
        super();
        this.region = region;
        
        // For demo purposes, we simulate the AWS Secrets Manager service
        // In production, this would use:
        // const { SecretsManagerClient, GetSecretValueCommand } = require('@aws-sdk/client-secrets-manager');
        // this.client = new SecretsManagerClient({ region });
        this.mockSecrets = mockSecrets || new Map();
    }

    /**
     * Seed a secret into the mock store (simulates AWS console/CLI creating a secret).
     * @param {string} secretName - The secret name
     * @param {Object} secretValue - The secret value object
     */
    seedSecret(secretName, secretValue) {
        this.mockSecrets.set(secretName, JSON.stringify(secretValue));
    }

    /**
     * Retrieve database credentials from AWS Secrets Manager.
     * 
     * @param {string} secretName - The name/ARN of the secret (e.g., "prod/BillingDatabase/Credentials")
     * @returns {Promise<{username: string, password: string}>} Database credentials
     * @throws {SecretNotFoundError} If the secret doesn't exist
     * @throws {PermissionError} If the IAM role lacks permission
     */
    async getDatabaseCredentials(secretName) {
        // In production, this would be:
        // const command = new GetSecretValueCommand({ SecretId: secretName });
        // const response = await this.client.send(command);
        // const secret = JSON.parse(response.SecretString);
        
        // Simulate IAM permission check
        if (!this.mockSecrets.has(secretName)) {
            // Check if it's an AccessDenied vs NotFound
            if (secretName.includes('Denied')) {
                throw new PermissionError(
                    `IAM role lacks permission to access secret '${secretName}'. `
                    + "Grant 'secretsmanager:GetSecretValue' permission."
                );
            }
            throw new SecretNotFoundError(`Secret '${secretName}' not found`);
        }
        
        const secretString = this.mockSecrets.get(secretName);
        return JSON.parse(secretString);
    }
}

/**
 * MOCK IMPLEMENTATION: For unit testing without AWS.
 * 
 * This allows developers to test their code without needing AWS credentials.
 * In production, this would be replaced with the real AwsSecretsManagerAdapter.
 */
export class MockSecretsProvider extends SecretsPort {
    /**
     * @param {Map<string, {username: string, password: string}>} mockSecrets 
     */
    constructor(mockSecrets) {
        super();
        this.mockSecrets = mockSecrets || new Map();
    }
    
    /**
     * Retrieve from mock storage.
     * @param {string} secretName 
     */
    async getDatabaseCredentials(secretName) {
        if (!this.mockSecrets.has(secretName)) {
            throw new SecretNotFoundError(`Mock secret '${secretName}' not found`);
        }
        return this.mockSecrets.get(secretName);
    }
}
/**
 * DOMAIN LAYER - Secrets Port (Interface)
 * 
 * This defines the contract for retrieving secrets. The application layer
 * depends on this abstraction, not on any specific secrets manager implementation.
 * 
 * ARCHITECTURAL NOTE:
 * This is a Port in Hexagonal Architecture. The application core defines
 * WHAT it needs (an interface), and the infrastructure layer provides
 * HOW it's implemented (AWS Secrets Manager, HashiCorp Vault, etc.).
 */

export class SecretsPort {
    /**
     * Retrieve database credentials from the secrets manager.
     * 
     * @abstract
     * @param {string} secretName - The name/ARN of the secret to retrieve
     * @returns {Promise<{username: string, password: string}>} Database credentials
     * @throws {Error} If the secret doesn't exist or IAM role lacks permission
     */
    async getDatabaseCredentials(secretName) {
        throw new Error('Not implemented');
    }
}

export class SecretNotFoundError extends Error {
    constructor(message) {
        super(message);
        this.name = 'SecretNotFoundError';
    }
}

export class PermissionError extends Error {
    constructor(message) {
        super(message);
        this.name = 'PermissionError';
    }
}
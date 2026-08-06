package com.grokkingsoftwarearchitecture.chapter11.section_11_4_secrets_management.secure.core.ports;

/**
 * Raised when a requested secret cannot be found.
 */
public class SecretNotFoundException extends RuntimeException {
    public SecretNotFoundException(String message) {
        super(message);
    }
}
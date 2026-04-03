package com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.core.ports;

/**
 * PORT – Defines "What" we need (lives in Core).
 * The interface acts as the "Socket."
 */
public interface PriceProviderPort {
    double getBitcoinPrice() throws Exception;
}
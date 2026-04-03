package com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.infrastructure.adapters;

import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.core.ports.PriceProviderPort;
/**
 * ADAPTER 1: The "Airplane Mode" / Test Adapter.
 */
public class FakePriceProvider implements PriceProviderPort {
    private final double fixedPrice;

    public FakePriceProvider(double fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    @Override
    public double getBitcoinPrice() {
        return fixedPrice;
    }
}
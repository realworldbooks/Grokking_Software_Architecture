package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.adapters;

import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.ports.AlertPort;
import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.externallibs.TwilioClient;
import com.grokkingsoftwarearchitecture.chapter05.shared.LogManager;

/**
 * THE ADAPTER (Production).
 * Bridges the internal AlertPort to the external Twilio API.
 * This class is the 'Clarity Engineer's' bridge between the inside and outside worlds.
 */
public class TwilioAdapter implements AlertPort {
    private final TwilioClient client;
    private final String targetPhoneNumber;

    /**
     * Configuration is injected here, keeping 'God Mode' keys out of the Core.
     * We initialize the client once to ensure efficient resource usage.
     */
    public TwilioAdapter(String apiKey, String targetPhoneNumber) { 
        this.client = new TwilioClient(apiKey); 
        this.targetPhoneNumber = targetPhoneNumber; 
    }

    @Override
    public void sendAlert(String message) {
        // Using the pre-configured client instead of creating a new one every time.
        client.sendSms(targetPhoneNumber, message);
        LogManager.info(TwilioAdapter.class, "(PROD ADAPTER) SMS sent via Twilio: {0}", message);
    }
}
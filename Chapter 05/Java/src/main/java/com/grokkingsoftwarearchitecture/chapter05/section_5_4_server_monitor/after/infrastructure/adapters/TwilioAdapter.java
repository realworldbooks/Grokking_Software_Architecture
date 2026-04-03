package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.adapters;

import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.ports.AlertPort;
import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.externallibs.TwilioClient;
import com.grokkingsoftwarearchitecture.chapter05.shared.LogManager;

/**
 * THE ADAPTER (Production).
 * This class is the 'Clarity Engineer's' bridge between the internal 
 * AlertPort and the external Twilio API.
 */
public class TwilioAdapter implements AlertPort {
    private final String apiKey; 
    private final String targetPhoneNumber;

    /**
     * Configuration is injected here, keeping 'God Mode' keys out of the Core.
     */
    public TwilioAdapter(String apiKey, String targetPhoneNumber) { 
        this.apiKey = apiKey; 
        this.targetPhoneNumber = targetPhoneNumber; 
    }

    @Override
    public void sendAlert(String message) {
        // We encapsulate the 'Chaotic' 3rd party SDK here.
        TwilioClient client = new TwilioClient(apiKey);
        client.sendSms(targetPhoneNumber, message);
        LogManager.info(TwilioAdapter.class, "(PROD ADAPTER) SMS sent via Twilio: {0}", message);
    }
}
package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.externallibs;


/**
 * Mock of a 3rd party SMS library.
 * Represents a stateful external SDK that requires authentication.
 */
public class TwilioClient {
    private final String key;

    /**
     * FIX: The constructor name now matches the class name 'TwilioClient'.
     * @param key The secret key used for authentication.
     */
    public TwilioClient(String key) {
        // FIX: The blank final field is now initialized.
        this.key = key;
    }

    /**
     * Simulates sending an SMS message.
     * @param to The recipient phone number.
     * @param msg The body of the text message.
     */
    public void sendSms(String to, String msg) {
        // By logging the parameters, we 'use' them to satisfy the compiler
        // and provide diagnostic feedback for the demo.
        System.out.println("[Twilio SDK] Using Key: " + this.key + " to send message to " + to + ": " + msg);
    }
}
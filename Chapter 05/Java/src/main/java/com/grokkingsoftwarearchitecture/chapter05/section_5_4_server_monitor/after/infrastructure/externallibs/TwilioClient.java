package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.externallibs;

/**
 * Mock of a 3rd party SMS library.
 */
public class TwilioClient {

    public TwilioClient(String key) {
        // key is intentionally not stored here in this mock
    }

    public void sendSms(String to, String msg) {
        // Simulation of a network call
    }
}
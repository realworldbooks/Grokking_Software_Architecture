package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.before;
/**
 * The Core Business Logic.
 * This class currently fails as a "Boundary Keeper" because it allows
 * external infrastructure details to leak into the domain.
 */
public class ServerMonitor {
    
    /**
     * Evaluates server health based on temperature.
     * @param temp The temperature value to check.
     */
    public void checkTemperature(int temp) {
        // VIOLATION: Hardcoded magic number.
        if (temp > 95) {
            // VIOLATION: Direct Dependency.
            // By hardcoding the 'TwilioClient', we have abandoned our post
            // as a Clarity Engineer.
            TwilioClient twilio = new TwilioClient("API_KEY");
            twilio.sendSms("555-1234", "Server is overheating!");
        } else {
            System.out.println("Temp " + temp + " is nominal.");
        }
    }
}

/**
 * Simulates a third-party Library.
 */
class TwilioClient {
    public TwilioClient(String key) { }
    public void sendSms(String to, String body) {
        System.out.println("[Twilio API] Sending SMS to " + to + ": " + body);
    }
}
package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.before;

import com.grokkingsoftwarearchitecture.chapter05.shared.LogManager;
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
            LogManager.info(ServerMonitor.class, "Temp {0} is nominal.", temp);
        }
    }
}

/**
 * Simulates a third-party Library.
 */
class TwilioClient {
    public TwilioClient(String key) { }
    public void sendSms(String to, String body) {
        LogManager.info(TwilioClient.class, "[Twilio API] Sending SMS to {0}: {1}", to, body);
    }
}
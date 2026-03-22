package com.grokkingsoftwarearchitecture.chapter04;

/**
 * INFRASTRUCTURE LAYER: EXTERNAL SERVICE.
 * ARCHITECTURE NOTE: Directly instantiating an SMTP service 
 * inside a Controller makes the code slow and fragile.
 */
public class SmtpEmailService {
    public void send(String email, String message) {
        System.out.println("  [Email] SMTP Logic: Sending '" 
            + message + "' to " + email);
    }
}
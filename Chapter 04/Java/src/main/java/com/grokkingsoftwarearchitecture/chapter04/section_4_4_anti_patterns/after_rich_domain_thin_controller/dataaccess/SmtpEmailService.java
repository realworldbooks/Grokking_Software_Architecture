package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.dataaccess;

/**
 * ARCHITECTURE NOTE: By isolating Email logic here, we prevent 
 * database concerns from "leaking" into the Presentation or 
 * Business layers.
 */
// Concrete implementation for an email provider
public class SmtpEmailService implements EmailService {
    @Override
    public void send(String to, String sub, String body) { }
}
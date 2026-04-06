package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;

import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;

/**
 * INFRASTRUCTURE LAYER: EXTERNAL SERVICE.
 * ARCHITECTURE NOTE: Directly instantiating an SMTP service 
 * inside a Controller makes the code slow and fragile.
 */
public class SmtpEmailService {
    public void send(String email, String message) {
        LogManager.info(SmtpEmailService.class, "  [Email] SMTP Logic: Sending '{0}' to {1}",
            message, email);
    }
}
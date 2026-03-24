package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.interfaces;

public interface EmailService {
    void send(String to, String subject, String body);
}
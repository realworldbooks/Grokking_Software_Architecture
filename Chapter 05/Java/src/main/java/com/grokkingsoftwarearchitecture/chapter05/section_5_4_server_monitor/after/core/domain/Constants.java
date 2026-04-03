package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.domain;
/**
 * Global threshold for server temperature alerts.
 * Acts as a single source of truth for the domain.
 */
public final class Constants {
    private Constants() {} // Prevent instantiation
    
    public static final int HIGH_TEMP_THRESHOLD = 95;
}
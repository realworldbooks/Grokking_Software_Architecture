package com.grokkingsoftwarearchitecture.chapter04.section_4_3_cross_cutting_concerns.after;

/**
 * 1. THE ABSTRACTION (The Contract).
 * ARCHITECTURE NOTE: In Java, we omit the 'I' prefix. This 
 * interface defines the "role" of a logger.
 */
public interface Logger {
    void log(String message);
}
package com.grokkingsoftwarearchitecture.chapter04.section_4_3_cross_cutting_concerns.after;

/**
 * A concrete implementation of the contract.
 */
public class FileLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("(AFTER_LOGGER) File Log: " + message);
    }
}
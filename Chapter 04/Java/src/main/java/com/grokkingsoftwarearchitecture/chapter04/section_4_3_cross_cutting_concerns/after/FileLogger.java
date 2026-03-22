package com.grokkingsoftwarearchitecture.chapter04;

/**
 * A concrete implementation of the contract.
 */
public class FileLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("(AFTER_LOGGER) File Log: " + message);
    }
}
package com.grokkingsoftwarearchitecture.chapter05.shared;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * SHARED UTILITY.
 * Centralizes the creation of loggers to ensure consistent 
 * formatting and level control across all architectural layers.
 */
public final class LogManager {
    private LogManager() {} // Prevent instantiation

    /**
     * Standardized way to retrieve a logger for any class.
     */
    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getName());
    }

    /**
     * Utility for a standardized info log to keep the Core clean.
     */
    public static void info(Class<?> clazz, String message, Object... params) {
        getLogger(clazz).log(Level.INFO, message, params);
    }
}
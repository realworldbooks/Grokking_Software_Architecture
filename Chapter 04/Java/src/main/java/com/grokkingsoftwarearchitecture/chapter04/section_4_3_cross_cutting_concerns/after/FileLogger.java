package com.grokkingsoftwarearchitecture.chapter04.section_4_3_cross_cutting_concerns.after;

import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;

/**
 * A concrete implementation of the contract.
 */
public class FileLogger implements Logger {
    @Override
    public void log(String message) {
        LogManager.info(FileLogger.class, "(AFTER_LOGGER) File Log: {0}", message);
    }
}
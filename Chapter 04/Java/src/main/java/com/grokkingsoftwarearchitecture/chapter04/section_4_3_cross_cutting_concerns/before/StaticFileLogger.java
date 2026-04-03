package com.grokkingsoftwarearchitecture.chapter04.section_4_3_cross_cutting_concerns.before;

import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;

/**
 * ANTI-PATTERN: THE STATIC GOD.
 * ARCHITECTURE NOTE: Static utilities like this are global state.
 * They create "Hidden Dependencies" because they are called
 * internally without being declared in a constructor.
 */
public class StaticFileLogger {

    private StaticFileLogger() {
        // Private constructor to hide the implicit public one
    }

    public static void log(String message) {
        LogManager.info(StaticFileLogger.class, "(BEFORE_LOGGER) Static Log: {0}", message);
    }
}
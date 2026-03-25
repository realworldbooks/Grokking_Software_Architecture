package com.grokkingsoftwarearchitecture.chapter04.section_4_3_cross_cutting_concerns.before;

/**
 * ANTI-PATTERN: THE STATIC GOD.
 * ARCHITECTURE NOTE: Static utilities like this are global state.
 * They create "Hidden Dependencies" because they are called
 * internally without being declared in a constructor.
 */
public class StaticFileLogger {
    public static void log(String message) {
        System.out.println("(BEFORE_LOGGER) Static Log: " + message);
    }
}
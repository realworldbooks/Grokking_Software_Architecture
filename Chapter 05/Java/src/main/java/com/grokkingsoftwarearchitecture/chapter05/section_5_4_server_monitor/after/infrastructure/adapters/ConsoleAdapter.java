package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.adapters;

import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.ports.AlertPort;

/**
 * ADAPTER 2: The "Dev" Adapter.
 * This adapter is perfect for local development. It proves that 
 * the Core doesn't care if the alert goes to a multi-million dollar cloud 
 * messaging service or simply prints to the local screen.
 */
public class ConsoleAdapter implements AlertPort {

    // ANSI escape codes to mimic C#'s Console.ForegroundColor
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    @Override
    public void sendAlert(String message) {
        // We wrap the message in the ANSI codes to print it in red,
        // then immediately reset the color so we don't bleed into other logs.
        System.out.println(ANSI_RED + "(DEV ADAPTER) ALERT: " + message + ANSI_RESET);
    }
}
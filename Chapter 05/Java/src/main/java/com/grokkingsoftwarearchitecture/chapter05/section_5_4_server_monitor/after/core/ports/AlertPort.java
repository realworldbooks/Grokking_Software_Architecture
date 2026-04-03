package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.ports;

/**
 * THE PORT.
 * Defines the architectural boundary. This interface is owned by 
 * the Domain, forcing the Infrastructure to adapt to IT.
 */
public interface AlertPort {
    /**
     * Sends an alert message. 
     * Implementations could be SMS, Email, or Async Cloud Events.
     */
    void sendAlert(String message);
}
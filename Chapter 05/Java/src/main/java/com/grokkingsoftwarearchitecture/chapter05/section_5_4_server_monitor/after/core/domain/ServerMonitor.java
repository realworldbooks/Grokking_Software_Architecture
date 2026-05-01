package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.domain;

import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.ports.AlertPort;

public class ServerMonitor {
    private final AlertPort alertPort;

    public ServerMonitor(AlertPort alertPort) {
        this.alertPort = alertPort;
    }

    public void checkTemperature(int temp) {
        if (temp > Constants.HIGH_TEMP_THRESHOLD) {
            alertPort.sendAlert("Temp is " + temp + " degrees! Take cover!");
        } else {
            System.out.println("[Core] Temp " + temp + " is normal.");
        }
    }
}
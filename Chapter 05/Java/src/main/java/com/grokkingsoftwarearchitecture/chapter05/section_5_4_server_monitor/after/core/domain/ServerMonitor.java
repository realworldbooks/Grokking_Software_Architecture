package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.domain;

import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.ports.AlertPort;
import com.grokkingsoftwarearchitecture.chapter05.shared.LogManager; //Shared Import for cross-cutting concern

public class ServerMonitor {
    private final AlertPort alertPort;

    public ServerMonitor(AlertPort alertPort) {
        this.alertPort = alertPort;
    }

    public void checkTemperature(int temp) {
        if (temp > Constants.HIGH_TEMP_THRESHOLD) {
            alertPort.sendAlert("Temp is " + temp + " degrees! Take cover!");
        } else {
            // Using the shared cross-cutting concern
            LogManager.info(ServerMonitor.class, "[Core] Temp {0} is normal.", temp);
        }
    }
}
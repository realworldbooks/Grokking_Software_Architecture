package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.tests;

import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.ports.AlertPort;
import java.util.ArrayList;
import java.util.List;

public class FakeAlertPort implements AlertPort {
    private final List<String> sentMessages = new ArrayList<>();

    @Override
    public void sendAlert(String message) {
        sentMessages.add(message);
    }

    public List<String> getSentMessages() {
        return sentMessages;
    }
}
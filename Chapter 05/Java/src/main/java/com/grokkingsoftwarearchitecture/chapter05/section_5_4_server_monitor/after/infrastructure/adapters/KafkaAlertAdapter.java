package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.adapters;

import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.ports.AlertPort;
import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.externallibs.Producer;
import java.time.Instant;

/**
 * ADAPTER 3: The "Scale" Adapter (Async Messaging).
 * Wraps a messaging producer to allow the system to scale via a message broker.
 */
public class KafkaAlertAdapter implements AlertPort {
    private final Producer<String, String> kafkaProducer;

    public KafkaAlertAdapter(Producer<String, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void sendAlert(String message) {
        String payload = String.format("{\"Error\": \"%s\", \"Timestamp\": \"%s\"}", 
                                        message, Instant.now());
        
        // Passing a Key ("Server-01") ensures message order for this specific server.
        kafkaProducer.produce("Server-01", "server-alerts-topic", payload);
    }
}
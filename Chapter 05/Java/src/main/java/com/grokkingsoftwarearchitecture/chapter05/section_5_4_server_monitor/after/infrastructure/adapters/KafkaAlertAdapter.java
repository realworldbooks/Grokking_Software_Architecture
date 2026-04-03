package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.adapters;

import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.core.ports.AlertPort;
import com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.externallibs.Producer;
import java.time.Instant;

/**
 * ADAPTER 3: The "Scale" Adapter (Async Messaging).
 */
public class KafkaAlertAdapter implements AlertPort {
    private final Producer<String> kafkaProducer;

    public KafkaAlertAdapter(Producer<String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void sendAlert(String message) {
        // In a real app, use a JSON library like Jackson
        String payload = String.format("{\"Error\": \"%s\", \"Timestamp\": \"%s\"}", 
                                        message, Instant.now());
        
        kafkaProducer.produce("server-alerts-topic", payload);
    }
}
package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.externallibs;

import com.grokkingsoftwarearchitecture.chapter05.shared.LogManager;

public class FakeKafkaProducer implements Producer<String> {
    @Override
    public void produce(String topic, String value) {
        // Standardized logging across the infrastructure layer
        LogManager.info(FakeKafkaProducer.class, "[Kafka] Pushed to {0}: {1}", topic, value);
    }
}
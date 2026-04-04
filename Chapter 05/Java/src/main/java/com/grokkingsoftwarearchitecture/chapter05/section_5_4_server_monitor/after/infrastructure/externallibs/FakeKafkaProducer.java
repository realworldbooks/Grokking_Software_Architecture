package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.externallibs;

import com.grokkingsoftwarearchitecture.chapter05.shared.LogManager;

/**
 * Concrete implementation of a 3rd party Messaging Producer for demonstration.
 */
public class FakeKafkaProducer implements Producer<String, String> {
    @Override
    public void produce(String key, String topic, String value) {
        LogManager.info(FakeKafkaProducer.class, "[Kafka] Key: {0} | Topic: {1} | Data: {2}", key, topic, value);
    }
}
package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.externallibs;


/**
 * Concrete implementation of a 3rd party Messaging Producer for demonstration.
 */
public class FakeKafkaProducer implements Producer<String, String> {
    @Override
    public void produce(String key, String topic, String value) {
        System.out.println("[Kafka] Key: " + key + " | Topic: " + topic + " | Data: " + value);
    }
}
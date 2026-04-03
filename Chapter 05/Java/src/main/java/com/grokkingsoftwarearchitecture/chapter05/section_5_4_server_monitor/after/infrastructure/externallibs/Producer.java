package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.externallibs;
/**
 * Interface representing a 3rd party Messaging Producer (like Kafka).
 */
public interface Producer<V> {
    void produce(String topic, V value);
}
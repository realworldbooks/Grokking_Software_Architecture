package com.grokkingsoftwarearchitecture.chapter05.section_5_4_server_monitor.after.infrastructure.externallibs;

/**
 * Interface representing a 3rd party Messaging Producer (like Kafka).
 * Uses generics for both Key (K) and Value (V).
 */
public interface Producer<K, V> {
    void produce(K key, String topic, V value);
}
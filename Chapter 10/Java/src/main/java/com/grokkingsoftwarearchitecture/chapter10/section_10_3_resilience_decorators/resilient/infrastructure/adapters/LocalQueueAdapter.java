package com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient.infrastructure.adapters;

import com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient.core.ports.MessageQueue;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import java.util.Map;
import java.util.UUID;

/**
 * PHYSICAL INFRASTRUCTURE ADAPTER (Zero-Server Local Queue):
 * * @description 
 * This adapter uses H2's MVStore to create a disk-backed queue. It provides 
 * durability for the laboratory environment without requiring external brokers 
 * or cloud services.
 * * PRODUCTION ARCHITECTURE (How this works on a real server):
 * In a live production environment (e.g., using RabbitMQ or AWS SQS), 
 * this 'enqueue' method functions as follows:
 * * 1. THE CONNECTION: Instead of a local file path, the adapter opens a 
 * socket connection to the broker cluster.
 * 2. SERIALIZATION: The 'payload' Map is serialized into JSON or a 
 * binary format like Protobuf to be sent over the wire.
 * 3. ACKNOWLEDGMENT: The server sends back an 'Ack' once the message 
 * is persisted to its own distributed storage.
 * 4. ISOLATION: The Application Core is released immediately, while 
 * a separate Worker process eventually pulls the message.
 */
public class LocalQueueAdapter implements MessageQueue {
    private final String dbPath;

    public LocalQueueAdapter(String dbPath) {
        this.dbPath = dbPath;
    }

    @Override
    public void enqueue(Map<String, Object> payload) {
        // Open the physical store
        try (MVStore s = new MVStore.Builder().fileName(dbPath).open()) {
            MVMap<String, Map<String, Object>> map = s.openMap("payment_queue");
            
            // Use a random key for the queue entry
            map.put(UUID.randomUUID().toString(), payload);
            
            // Physically flush to disk
            s.commit();
        }

        System.out.println("      [Local Queue] DATA PERSISTED: Message secured in " + dbPath);
    }
}
const AlertPort = require('../../core/ports/alertPort');

/**
 * ADAPTER 3: The "Scale" Adapter (Async Messaging).
 * Implements the AlertPort by pushing structured JSON to a Kafka topic.
 */
class KafkaAdapter extends AlertPort {
    /**
     * Connects the adapter to a messaging producer.
     * * @param kafkaProducer The 3rd party messaging client.
     */
    constructor(kafkaProducer) {
        super();
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * Transforms the domain message into a JSON payload for the broker.
     * * @param message The alert content.
     */
    sendAlert(message) {
        const payload = JSON.stringify({
            Error: message,
            Timestamp: new Date().toISOString()
        });
        
        // Using a static key ensures partition affinity for chronological order.
        this.kafkaProducer.produce("Server-01", "server-alerts-topic", payload);
    }
}

module.exports = KafkaAdapter;
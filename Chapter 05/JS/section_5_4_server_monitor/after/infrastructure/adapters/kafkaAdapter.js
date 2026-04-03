const AlertPort = require('../../core/ports/alertPort');

/**
 * ADAPTER 3: The "Scale" Adapter (Async Messaging).
 * Shows how easy it is to swap a "Sync" SMS for an "Async" message.
 */
class KafkaAdapter extends AlertPort {
    constructor(kafkaProducer) {
        super();
        this.kafkaProducer = kafkaProducer;
    }

    sendAlert(message) {
        const payload = JSON.stringify({
            Error: message,
            Timestamp: new Date().toISOString()
        });
        
        // Fire and forget
        this.kafkaProducer.produce("server-alerts-topic", payload);
    }
}

module.exports = KafkaAdapter;
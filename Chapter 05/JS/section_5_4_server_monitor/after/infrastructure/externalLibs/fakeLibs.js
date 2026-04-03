/**
 * Dummies to allow execution without installing real npm packages.
 * This represents the "Chaotic Outside World".
 */
class TwilioClient {
    constructor(key) {
        this.key = key;
    }
    sendSms(to, msg) {
        // Simulation of a network call
    }
}

class FakeKafkaProducer {
    produce(topic, value) {
        console.log(`[Kafka] Pushed to ${topic}: ${value}`);
    }
}

module.exports = { TwilioClient, FakeKafkaProducer };
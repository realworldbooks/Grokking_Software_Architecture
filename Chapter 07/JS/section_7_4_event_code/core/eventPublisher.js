/**
 * THE ABSTRACTION: This keeps your domain logic completely decoupled.
 * The Controller will depend on this abstraction so it has no idea whether 
 * it is publishing to an RxJS Subject, a RabbitMQ cluster, or AWS EventBridge.
 */
export class EventPublisher {
    /**
     * @param event - The event to publish into the stream.
     */
    publish(event) {
        throw new Error("Method 'publish()' must be implemented by the infrastructure layer.");
    }
}
package chapter07.eventcode.infrastructure;

import chapter07.eventcode.core.EventPublisher;
import chapter07.eventcode.shared.Event;
import chapter07.eventcode.shared.OrderPlaced;
import chapter07.eventcode.handlers.ShippingLabelPrinter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.CompletableFuture;

/**
 * THE SHOCK ABSORBER: Simulates a real message broker (like RabbitMQ or Azure Service Bus).
 * This infrastructure layer completely decouples the Producer from the Consumer.
 */
public class InMemoryBroker implements EventPublisher {
    
    // We use Java's BlockingQueue to act as an in-memory queue. 
    // It safely holds messages in memory until a background consumer asks for them.
    private final BlockingQueue<Event> _channel = new LinkedBlockingQueue<>();
    private final ShippingLabelPrinter _shippingService = new ShippingLabelPrinter();

    /**
     * Accepts a message and immediately returns control to the caller.
     */
    @Override
    public <T extends Event> void publishAsync(T event) {
        _channel.offer(event);
    }

    /**
     * Simulates the background worker continuously pulling new messages as they arrive,
     * completely independent of the web request lifecycle.
     */
    public void startListeningAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                while (true) {
                    Event event = _channel.take();
                    if (event instanceof OrderPlaced orderPlaced) {
                        _shippingService.handleAsync(orderPlaced).join();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
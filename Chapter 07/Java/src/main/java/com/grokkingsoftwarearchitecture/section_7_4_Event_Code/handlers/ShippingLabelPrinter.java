package chapter07.eventcode.handlers;

import chapter07.eventcode.shared.OrderPlaced;
import java.util.concurrent.CompletableFuture;

/**
 * THE CONSUMER: A background worker simulating an independent microservice.
 * It operates entirely on its own timeline, processing work as fast as it can.
 * This replaces the Controller Action as the entry point for your logic.
 */
public class ShippingLabelPrinter implements Consumer<OrderPlaced> {
    @Override
    public CompletableFuture<Void> handleAsync(OrderPlaced event) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("\n[Shipping Service] Waking up...");
            
            // We extract the Correlation ID to link this background work 
            // back to the original Web API request in our logs.
            System.out.println("[Shipping Service] CorrelationId matched: " + event.correlationId());
            System.out.println("[Shipping Service] Printing Label for Order: " + event.orderId());
            System.out.printf("[Shipping Service] Package Value: $%.2f%n", event.totalAmount());
            
            // Simulate the time it takes to generate and print a label
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // The Handshake: Telling the broker the job is successfully done
            System.out.println("[Shipping Service] Label Printed! ACK sent to broker.\n");
        });
    }
}
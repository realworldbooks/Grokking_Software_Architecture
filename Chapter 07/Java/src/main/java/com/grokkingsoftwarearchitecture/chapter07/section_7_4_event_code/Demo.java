package com.grokkingsoftwarearchitecture.chapter07.section_7_4_event_code;

import com.grokkingsoftwarearchitecture.chapter07.section_7_4_event_code.infrastructure.InMemoryBroker;
import com.grokkingsoftwarearchitecture.chapter07.section_7_4_event_code.controllers.OrderController;
import java.util.UUID;

/**
 * THE COMPOSITION ROOT: Where the application wires all the decoupled pieces 
 * together and executes the simulation.
 */
public class Demo {
    public static void runAsync() throws InterruptedException {
        System.out.println("=== Section 7.4: Event Definition & Decoupling ===\n");

        // 1. Wire up the Shared Infrastructure (The Broker)
        InMemoryBroker broker = new InMemoryBroker();

        // 2. Spin up the Consumer in the background (simulating a separate microservice)
        // We use an asynchronous thread so it operates independently.
        broker.startListeningAsync();

        // 3. Instantiate the API Service (The Producer)
        OrderController orderController = new OrderController(broker);

        // 4. Simulate the user clicking "Checkout"
        orderController.checkoutAsync(UUID.randomUUID(), 149.99);

        // Wait enough time for the background queue to process the label printing
        Thread.sleep(2000);
        
        System.out.println("Press any key to return to menu...");
    }
}
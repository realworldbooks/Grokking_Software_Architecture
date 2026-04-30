const Order = require('./order');
const OrderService = require('./orderService');

/**
 * The Execution Layer.
 * * ARCHITECTURE NOTE:
 * This class acts as the "Chief Explainer," demonstrating how 
 * static, global dependencies lead to Tight Coupling.
 */
class Demo {
    /**
     * THE STATIC ENTRY POINT:
     * * This satisfies the lab orchestrator's requirement for a 
     * consistent 'run' interface across all project types.
     */
    static run() {
        console.log("--- STARTING SCENARIO: STATIC LOGGER (BEFORE) ---");

        // ARCHITECTURAL CRITIQUE:
        // In the 'Before' state, we don't pass anything into the constructor.
        // The OrderService 'reaches out' to a global static logger.
        // This makes the service hard to test and violates the 
        // Dependency Inversion Principle.
        const service = new OrderService();

        console.log("Executing Order Save...");
        
        // This will trigger a static console log or file write 
        // that we cannot easily intercept.
        service.saveOrder(new Order());

        console.log("\n--- SCENARIO COMPLETE ---");
    }
}

/**
 * BOOTSTRAP LOGIC:
 * Allows the file to be run directly via 'node demo.js' while remaining 
 * compatible with the Chapter orchestrator.
 */
if (require.main === module) {
    Demo.run();
}

module.exports = Demo;
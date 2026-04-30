const Order = require('./order');
const OrderService = require('./orderService');
const FileLogger = require('./fileLogger');

/**
 * The Execution Layer.
 * * ARCHITECTURE NOTE:
 * This class acts as the "Chief Explainer," demonstrating the 
 * power of Dependency Injection and the Downward Dependency Rule.
 */
class Demo {
    /**
     * THE STATIC ENTRY POINT:
     * * This satisfies the lab orchestrator's requirement for a 
     * consistent 'run' interface across all project types.
     */
    static run() {
        console.log("--- STARTING SCENARIO: INJECTED LOGGER (AFTER) ---");
        
        // Step 1: Setup - Instantiate the concrete infrastructure.
        // We are allowed to know about FileLogger here because this 
        // is the Composition Root.
        const logger = new FileLogger();
        
        // Step 2: Injection - Pass the logger into the service.
        // The OrderService is now 'blind' to the specific logging 
        // destination (File, Console, or Database).
        const service = new OrderService(logger);
        
        // Step 3: Execution - Process the domain request.
        console.log("Executing Order Save...");
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
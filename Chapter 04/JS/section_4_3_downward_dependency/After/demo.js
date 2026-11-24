const Order = require('./order');
const OrderService = require('./orderService');
const SqlOrderRepository = require('./sqlOrderRepository');

/**
 * The Execution Layer.
 * * ARCHITECTURE NOTE:
 * This class acts as the "Chief Explainer," demonstrating the 
 * successful implementation of the Downward Dependency Rule.
 */
class Demo {
    /**
     * THE STATIC ENTRY POINT:
     * * This satisfies the lab orchestrator's requirement for a 
     * consistent 'run' interface across all project types.
     */
    static run() {
        console.log("--- STARTING SCENARIO: DOWNWARD DEPENDENCY (AFTER) ---");
        
        // Step 1: Setup - Instantiate the concrete infrastructure.
        // In the 'After' state, we are allowed to know about SQL here 
        // because this is the Composition Root.
        const repository = new SqlOrderRepository();
        
        // Step 2: Injection - Pass the infrastructure into the service.
        // The OrderService remains 'blind' to the database technology.
        const service = new OrderService(repository);
        
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
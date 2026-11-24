const SomeRepository = require('./someRepository');

/**
 * The Execution Layer.
 * * ARCHITECTURE NOTE:
 * This class acts as the "Chief Explainer," demonstrating the 
 * violation of the Downward Dependency Rule.
 */
class Demo {
    /**
     * THE STATIC ENTRY POINT:
     * * This satisfies the lab orchestrator's requirement for a 
     * consistent 'run' interface across all project types.
     */
    static run() {
        console.log("--- STARTING SCENARIO: UPWARD DEPENDENCY (BEFORE) ---");
        
        // ARCHITECTURAL CRITIQUE:
        // In the 'Before' state, our entry point is tightly coupled to 
        // the infrastructure. We are instantiating the concrete repository 
        // directly, which makes this code rigid and hard to test.
        const beforeRepo = new SomeRepository();
        
        console.log("Executing Data Update...");
        beforeRepo.updateData(123, "New Data");
        
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
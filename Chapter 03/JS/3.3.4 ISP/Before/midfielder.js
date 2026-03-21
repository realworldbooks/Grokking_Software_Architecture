/**
 * ANTI-PATTERN: The "Fat Interface" (ISP Violation).
 * * ARCHITECTURE PROBLEM: The Interface Segregation Principle dictates that 
 * no client should be forced to depend on methods it does not use. 
 * * In this "Before" state, the Midfielder is conceptually tied to a bloated 
 * definition of a "Player." Because the system expects every player to 
 * handle every type of training, the Midfielder is forced to carry 
 * around Goalie-specific logic. 
 * * WHY THIS FAILS:
 * - Fragility: Other parts of the system might see these methods and 
 * blindly call them, leading to runtime crashes.
 * - Code Rot: We are forced to write "dummy" implementations that only 
 * exist to throw errors, cluttering the class and confusing developers.
 */
class Midfielder {
    /**
     * A valid domain action for a field player.
     */
    practiceShooting() {
        console.log("  [Midfielder] Practicing shooting drills.");
    }

    /**
     * A valid domain action for a field player.
     */
    practiceTackling() {
        console.log("  [Midfielder] Practicing slide tackles.");
    }

    /**
     * 🚨 ARCHITECTURE WARNING: The "Fat Interface" trap.
     * The Midfielder doesn't play in the net, but the current 
     * design forces this method to exist.
     */
    practiceDivingSaves() {
        throw new Error("Midfielders don't play in the net!");
    }

    /**
     * 🚨 ARCHITECTURE WARNING: High Coupling to irrelevant logic.
     * This method exists only to satisfy a bloated conceptual contract.
     */
    practiceHandDistribution() {
        throw new Error("Midfielders can't use their hands!");
    }
}

module.exports = Midfielder;
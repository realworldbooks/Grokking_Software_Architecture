/**
 * THE CLEAN IMPLEMENTATION.
 * * ARCHITECTURE NOTE: Look at how clean this class is now! There are no 
 * Errors being thrown for "diving saves" or "hand distribution." By segregating 
 * the conceptual training roles, the Midfielder only implements what it 
 * actually does. The class is now highly cohesive and "honest."
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
}

module.exports = Midfielder;
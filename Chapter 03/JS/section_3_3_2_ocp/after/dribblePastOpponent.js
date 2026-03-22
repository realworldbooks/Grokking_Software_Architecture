/**
 * A Concrete Strategy.
 * * ARCHITECTURE NOTE: This class is a standalone implementation of a specific play.
 * By keeping it separate, we ensure that changes to the 'Dribble' logic 
 * never impact the 'Pass' logic or the 'Midfielder' core logic.
 */
class DribblePastOpponent {
    execute() {
        console.log("  [Action] Executing a dribble move…");
    }
}

module.exports = DribblePastOpponent;
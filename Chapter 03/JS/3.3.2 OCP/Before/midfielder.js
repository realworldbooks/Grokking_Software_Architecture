/**
 * ANTI-PATTERN: Violation of the Open/Closed Principle (OCP).
 * * ARCHITECTURE PROBLEM: The Open/Closed Principle states that a class should be 
 * "Open for extension, but Closed for modification." 
 * * In this "Before" state, the Midfielder is NOT closed. Every time the team 
 * learns a new play (like 'PassToStriker'), we are forced to open this file 
 * and add a new 'else if' block. 
 * * This is dangerous because we are modifying existing, tested code to add new 
 * features, which is the fastest way to introduce regressions into a system.
 */
class Midfielder {
    /**
     * Executes a play based on a hardcoded string. 
     * This creates a fragile, infinitely growing conditional chain.
     * @param {string} playName - The name of the play to execute.
     */
    executePlay(playName) {
        // 🚨 ARCHITECTURE WARNING: This if/else chain will grow forever.
        // Every time a new play is added, this class MUST be modified.
        if (playName === "DribblePastOpponent") {
            console.log("  [Action] Executing a dribble move…");
        } else if (playName === "DefensiveFormation") {
            console.log("  [Action] Getting into defensive position…");
        } 
        // 🚨 To add 'PassToStriker', we have to break into this file and edit it!
        else {
            console.log(`  [Error] Unknown play: ${playName}`);
        }
    }
}

module.exports = Midfielder;
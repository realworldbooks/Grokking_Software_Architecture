const Forward = require('./forward');
const Midfielder = require('./midfielder');

/**
 * ANTI-PATTERN: Violation of the Dependency Inversion Principle (DIP).
 * * ARCHITECTURE PROBLEM: High-level modules (Coach) should not depend on 
 * low-level modules (Forward/Midfielder). Both should depend on abstractions.
 * * By using 'require' and 'new' inside this class, the Coach is now 
 * "hard-wired" to these specific players. 
 * * WHY THIS FAILS:
 * - Rigid: You cannot swap a Forward for a Winger without editing the Coach file.
 * - Untestable: You cannot unit test the Coach without also creating real 
 * instances of the players. You are stuck with whatever is in those files.
 */
class Coach {
    constructor() {
        // 🚨 ARCHITECTURE WARNING: The 'new' keyword is glue. 
        // The Coach is taking on the responsibility of "manufacturing" its 
        // own dependencies instead of just using them.
        this.forward = new Forward();
        this.midfielder = new Midfielder();
    }

    executeGamePlan() {
        this.forward.attack();
        this.midfielder.controlMidfield();
    }
}

module.exports = Coach;
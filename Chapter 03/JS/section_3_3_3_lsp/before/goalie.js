const Player = require('./player');

/**
 * ANTI-PATTERN: Violation of the Liskov Substitution Principle (LSP).
 * * ARCHITECTURE PROBLEM: LSP states that objects of a superclass should 
 * be replaceable with objects of its subclasses without breaking the 
 * application.
 * * Here, the Goalie class inherits from Player but refuses to fulfill 
 * the field-play contract. While it doesn't crash the app immediately 
 * with a 'Not Implemented' error, it provides "surprising" behavior 
 * that deviates from what the base class promised.
 */
class Goalie extends Player {
    playFieldPosition() {
        // 🚨 ARCHITECTURE WARNING: A goalie doesn't play the field! 
        // By allowing Goalie to be a subclass of Player, we are 
        // lying to any consumer (like the Coach) who expects a 
        // field-capable athlete.
        console.log("  [Goalie] I can't do that! I stay near the net and use my hands.");
    }
}

module.exports = Goalie;
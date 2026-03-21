/**
 * A Low-Level Module.
 * * ARCHITECTURE NOTE: This is a concrete implementation. Because the Coach 
 * imports this file directly, any change to the 'attack' method name will 
 * force a breaking change in the Coach class.
 */
class Forward {
    attack() {
        console.log("  [Action] Forward is attacking.");
    }
}

module.exports = Forward;
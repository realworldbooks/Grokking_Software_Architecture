/**
 * A Low-Level Module.
 * * ARCHITECTURE NOTE: Similar to the Forward, this concrete class is 
 * directly coupled to the Coach. There is no "seam" or abstraction 
 * between the two.
 */
class Midfielder {
    controlMidfield() {
        console.log("  [Action] Midfielder is controlling the game.");
    }
}

module.exports = Midfielder;
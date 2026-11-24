const Player = require('./player');

/**
 * LSP SOLUTION: True Substitutability.
 * * ARCHITECTURE NOTE: Just like the Forward, the Midfielder is 100% 
 * compatible with the Player abstraction. We can swap one for the other 
 * at runtime without the Coach ever needing to know the difference.
 */
class Midfielder extends Player {
    playFieldPosition() {
        console.log("  [Midfielder] Controlling the midfield, passing and tackling.");
    }
}

module.exports = Midfielder;
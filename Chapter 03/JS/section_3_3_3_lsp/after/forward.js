const Player = require('./player');

/**
 * LSP SOLUTION: True Substitutability.
 * * ARCHITECTURE NOTE: The Forward is a true substitute for a Player. It 
 * doesn't provide "surprising" behavior or refuse the contract. When the 
 * Coach expects a field action, the Forward delivers exactly that.
 */
class Forward extends Player {
    playFieldPosition() {
        console.log("  [Forward] Leading the attack and trying to score.");
    }
}

module.exports = Forward;
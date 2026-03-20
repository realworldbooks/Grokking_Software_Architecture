const Player = require('./player');

class Midfielder extends Player {
    playFieldPosition() {
        console.log("  [Midfielder] Controlling the midfield, passing and tackling.");
    }
}

module.exports = Midfielder;
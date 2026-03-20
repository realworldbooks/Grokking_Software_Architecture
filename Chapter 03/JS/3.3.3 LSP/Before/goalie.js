const Player = require('./player');

class Goalie extends Player {
    playFieldPosition() {
        console.log("  [Goalie] I can't do that! I stay near the net and use my hands.");
    }
}

module.exports = Goalie;
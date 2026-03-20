const Player = require('./player');

class Forward extends Player {
    playFieldPosition() {
        console.log("  [Forward] Leading the attack and trying to score.");
    }
}

module.exports = Forward;
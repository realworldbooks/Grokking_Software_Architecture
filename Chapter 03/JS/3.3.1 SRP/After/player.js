// Responsibility 1: Manages only the player’s state and actions
class Player {
    constructor(name) {
        this.name = name;
    }

    dribbleBall() {
        console.log(`  [Action] ${this.name} is dribbling the ball down the court.`);
    }
}

module.exports = Player;
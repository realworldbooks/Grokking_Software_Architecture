class Player {
    constructor(name) {
        this.name = name;
    }

    // Responsibility 1: Player’s own state/abilities
    dribbleBall() {
        console.log(`  [Action] ${this.name} is dribbling the ball down the court.`);
    }

    // Responsibility 2: Tactical Logic
    determineBestPosition() {
        console.log(`  [Tactics] Calculating optimal court position for ${this.name}...`);
    }

    // Responsibility 3: Data Persistence
    saveStatsToDatabase() {
        console.log(`  [Database] Saving ${this.name}'s game stats to the database.`);
    }
}

module.exports = Player;
/**
 * ANTI-PATTERN: The "God Class" (SRP Violation).
 * * ARCHITECTURE PROBLEM: This class is doing too much. According to the Single 
 * Responsibility Principle, a class should have only one reason to change. 
 * This class currently has THREE:
 * * 1. If the physical rules of the game change (Action).
 * 2. If the AI algorithms change (Tactics).
 * 3. If the database schema or persistence logic changes (Persistence).
 * * In a JavaScript environment, this coupling makes unit testing difficult because 
 * you might just want to test a dribble move, but you're forced to carry around 
 * code that expects a database connection.
 */
class Player {
    constructor(name) {
        this.name = name;
    }

    /**
     * Responsibility 1: Domain Logic. 
     * (This is the core purpose of the entity.)
     */
    dribbleBall() {
        console.log(`  [Action] ${this.name} is dribbling the ball down the court.`);
    }

    /**
     * Responsibility 2: Tactical Logic.
     * (Should be moved to a dedicated service or strategy.)
     */
    determineBestPosition() {
        console.log(`  [Tactics] Calculating optimal court position for ${this.name}...`);
    }

    /**
     * Responsibility 3: Data Persistence.
     * (Strongly couples the domain model to infrastructure.)
     */
    saveStatsToDatabase() {
        console.log(`  [Database] Saving ${this.name}'s game stats to the database.`);
    }
}

module.exports = Player;
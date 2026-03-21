/**
 * Responsibility 1: State and Core Actions.
 * * ARCHITECTURE NOTE: By stripping out the database and tactical logic, the Player 
 * class is now highly cohesive. It has only one reason to change: if the fundamental 
 * definition of a player (like adding a 'pass' method or 'stamina' property) changes.
 * * This is now a "POJO" (Plain Old JavaScript Object) style entity that is 
 * incredibly easy to move between different parts of the system.
 */
class Player {
    constructor(name) {
        this.name = name;
    }

    /**
     * Executes a core domain action.
     */
    dribbleBall() {
        console.log(`  [Action] ${this.name} is dribbling the ball down the court.`);
    }
}

module.exports = Player;
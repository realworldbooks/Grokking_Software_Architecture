/**
 * DIP SOLUTION: Constructor Injection and Loose Coupling.
 * * ARCHITECTURE NOTE: The Coach is no longer responsible for building its own 
 * team. The 'new' keyword and hardcoded 'requires' have been completely removed!
 * * Instead, the Coach receives its dependencies as an argument. This is 
 * "Dependency Injection." Because the Coach only expects an array of objects 
 * with a .performAction() method, we can hand it any combination of players.
 * * This makes the Coach class infinitely extensible and trivial to unit test 
 * using mock player objects.
 */
class Coach {
    /**
     * Dependencies are provided from the outside.
     * @param {Array} players - A list of player objects.
     */
    constructor(players) {
        this.team = players;
    }

    executeGamePlan() {
        // The Coach now relies on a shared "contract" (performAction)
        // rather than specific concrete methods like .attack() or .tackle().
        for (const player of this.team) {
            player.performAction();
        }
    }
}

module.exports = Coach;
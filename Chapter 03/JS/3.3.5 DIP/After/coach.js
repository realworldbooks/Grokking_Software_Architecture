class Coach {
    constructor(players) {
        this.team = players;
    }

    executeGamePlan() {
        for (const player of this.team) {
            player.performAction();
        }
    }
}
module.exports = Coach;
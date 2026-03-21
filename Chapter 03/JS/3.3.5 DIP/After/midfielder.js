/**
 * A Low-Level Detail.
 * * ARCHITECTURE NOTE: The Midfielder is now a "plug-and-play" component. 
 * It no longer has a unique interface that the Coach must specifically 
 * account for.
 */
class Midfielder {
    performAction() {
        console.log("  [Action] Midfielder is controlling the game.");
    }
}

module.exports = Midfielder;
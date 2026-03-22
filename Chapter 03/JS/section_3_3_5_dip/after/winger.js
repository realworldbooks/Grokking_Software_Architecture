/**
 * DIP SOLUTION: The "Plug-and-Play" Proof.
 * * ARCHITECTURE NOTE: This class proves the system is now open for extension. 
 * We created a Winger and can immediately include it in the game plan. 
 * * Most importantly: We did not have to touch a single line of code in 
 * coach.js to add this new player type!
 */
class Winger {
    performAction() {
        console.log("  [Action] Winger is running down the sideline.");
    }
}

module.exports = Winger;
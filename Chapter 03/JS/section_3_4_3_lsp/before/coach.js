/**
 * THE CONSUMER (The Victim of the LSP Violation).
 * * ARCHITECTURE PROBLEM: The Coach trusts the abstraction of "Player". 
 * The Coach assumes that if they are handed a Player, calling 
 * playFieldPosition() will result in a field-based action. 
 * * Because the Goalie was substituted where a field player was 
 * expected, the Coach's game plan fails to achieve the desired 
 * outcome.
 */
class Coach {
    /**
     * Directs the player to take their field position.
     * @param {Player} fieldPlayer - An object expected to fulfill the Player contract.
     */
    directFieldPlay(fieldPlayer) {
        console.log("  [Coach] Alright player, execute your field assignment!");
        
        // 🚨 If a Goalie is passed here, the logic "breaks" because 
        // the Goalie's behavior is incompatible with a field assignment.
        fieldPlayer.playFieldPosition();
    }
}

module.exports = Coach;
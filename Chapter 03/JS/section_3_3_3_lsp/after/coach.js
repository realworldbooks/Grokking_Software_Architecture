/**
 * THE CONFIDENT CONSUMER.
 * * ARCHITECTURE NOTE: Because we strictly adhered to LSP, the Coach class 
 * becomes incredibly simple. We no longer need 'if' checks to see if a 
 * player is a Goalie, nor do we need to wrap calls in try/catch blocks 
 * to handle unexpected behavior. 
 * * The Coach trusts the abstraction because the architecture is now honest.
 */
class Coach {
    /**
     * Directs the player to take their field position with absolute certainty.
     * @param {Player} fieldPlayer - A guaranteed field-capable athlete.
     */
    directFieldPlay(fieldPlayer) {
        console.log("  [Coach] Alright player, execute your field assignment!");
        
        // This call is now safe, predictable, and architecturally sound.
        fieldPlayer.playFieldPosition();
    }
}

module.exports = Coach;
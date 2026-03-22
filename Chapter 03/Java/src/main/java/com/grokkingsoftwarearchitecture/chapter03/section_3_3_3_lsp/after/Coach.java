package com.grokkingsoftwarearchitecture.chapter03.lsp.after;

/**
 * The Confident Consumer.
 * * ARCHITECTURE NOTE: Because we strictly adhered to LSP, the Coach class is 
 * incredibly simple and clean. 
 * * Notice what is MISSING here: There are no "if (fieldPlayer instanceof Goalie)" checks. 
 * There are no try/catch blocks expecting an UnsupportedOperationException. The Coach 
 * trusts the abstraction 100%. If an object is passed in as a Player, the Coach 
 * knows for a fact it can play the field.
 */
public class Coach {
    
    /**
     * Directs the player to take their field position with absolute confidence.
     * * @param fieldPlayer A guaranteed field-capable player.
     */
    public void directFieldPlay(Player fieldPlayer) {
        System.out.println("  [Coach] Alright player, execute your field assignment!");
        
        // This will now ALWAYS succeed. No exceptions, no unexpected behavior.
        fieldPlayer.playFieldPosition();
    }
}
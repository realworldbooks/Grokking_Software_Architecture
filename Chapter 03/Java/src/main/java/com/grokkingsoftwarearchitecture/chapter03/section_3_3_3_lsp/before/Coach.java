package com.grokkingsoftwarearchitecture.chapter03.section_3_3_3_lsp.before;

/**
 * The Consumer (The Victim of the LSP Violation).
 * * ARCHITECTURE PROBLEM: The Coach is relying on the abstraction (Player). 
 * The Coach trusts that because the object is a Player, calling playFieldPosition() 
 * will work perfectly. Because the Goalie lied about its capabilities, the Coach's 
 * game plan is now broken.
 */
public class Coach {
    
    /**
     * Directs the player to take their field position.
     * * @param fieldPlayer Any player (or so the Coach thinks).
     */
    public void directFieldPlay(Player fieldPlayer) {
        System.out.println("  [Coach] Alright player, execute your field assignment!");
        
        // If a Goalie is passed in here, the system breaks!
        fieldPlayer.playFieldPosition();
    }
}
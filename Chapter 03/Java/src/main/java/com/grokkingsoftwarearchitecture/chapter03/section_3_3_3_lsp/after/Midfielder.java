package com.grokkingsoftwarearchitecture.chapter03.section_3_3_3_lsp.after;

/**
 * LSP SOLUTION: True Substitutability.
 * * ARCHITECTURE NOTE: Just like the Forward, the Midfielder fully supports the 
 * behavior expected of a generic Player. We can swap a Forward for a Midfielder 
 * at runtime, and the application will remain perfectly stable.
 */
public class Midfielder extends Player {
    
    /**
     * Flawlessly fulfills the base class contract.
     */
    @Override
    public void playFieldPosition() {
        System.out.println("  [Midfielder] Controlling the midfield, passing and tackling.");
    }
}
package com.grokkingsoftwarearchitecture.chapter03.lsp.after;

/**
 * LSP SOLUTION: True Substitutability.
 * * ARCHITECTURE NOTE: A Forward is a true substitute for a Player. It fully 
 * honors the contract set by the base class. It doesn't throw an 
 * UnsupportedOperationException or refuse to do the work.
 */
public class Forward extends Player {
    
    /**
     * Flawlessly fulfills the base class contract.
     */
    @Override
    public void playFieldPosition() {
        System.out.println("  [Forward] Leading the attack and trying to score.");
    }
}
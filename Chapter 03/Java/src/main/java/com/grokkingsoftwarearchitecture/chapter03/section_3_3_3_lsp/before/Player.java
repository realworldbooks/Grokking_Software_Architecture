package com.grokkingsoftwarearchitecture.chapter03.section_3_3_3_lsp.before;

/**
 * The Base Contract.
 * * ARCHITECTURE NOTE: By placing this method in the base abstract class, we are 
 * creating a strict contract: "Every single class that inherits from Player 
 * MUST be able to execute playFieldPosition() successfully."
 */
public abstract class Player {
    public abstract void playFieldPosition();
}
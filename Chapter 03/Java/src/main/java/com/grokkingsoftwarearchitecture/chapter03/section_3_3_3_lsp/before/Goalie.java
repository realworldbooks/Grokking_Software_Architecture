package com.grokkingsoftwarearchitecture.chapter03.lsp.before;

/**
 * ANTI-PATTERN: Violation of the Liskov Substitution Principle (LSP).
 * * ARCHITECTURE PROBLEM: LSP states that objects of a superclass should be 
 * replaceable with objects of its subclasses without breaking the application.
 * * This Goalie class claims to be a "Player", but it refuses to fulfill the 
 * "playFieldPosition()" contract set by the base class. Often, developers will 
 * throw an UnsupportedOperationException here. This means the Goalie CANNOT be safely 
 * substituted anywhere a Player is expected.
 */
public class Goalie extends Player {
    
    /**
     * Breaks the base class contract by refusing to play the field.
     */
    @Override
    public void playFieldPosition() {
        // 🚨 ARCHITECTURE WARNING: A goalie doesn't play the field! If the Coach 
        // calls this blindly, they get unexpected behavior or an outright crash.
        System.out.println("  [Goalie] I can't do that! I stay near the net and use my hands.");
    }
}
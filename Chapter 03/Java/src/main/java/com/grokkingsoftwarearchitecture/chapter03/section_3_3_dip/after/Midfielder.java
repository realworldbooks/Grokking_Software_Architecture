package com.grokkingsoftwarearchitecture.chapter03.dip.after;

/**
 * A Low-Level Detail.
 * * ARCHITECTURE NOTE: Just like the Forward, this concrete class depends 
 * on the Player abstraction rather than being hardcoded into the Coach.
 */
public class Midfielder implements Player {
    public void performAction() {
        System.out.println("  [Action] Midfielder is controlling the game.");
    }
}
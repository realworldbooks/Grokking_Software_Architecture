package com.grokkingsoftwarearchitecture.chapter03.dip.before;

/**
 * A Low-Level Module.
 * * ARCHITECTURE NOTE: Similar to the Forward, this concrete class is 
 * directly wired into the Coach. There is no abstraction, meaning the 
 * system is rigid and difficult to scale.
 */
public class Midfielder {
    public void controlMidfield() {
        System.out.println("  [Action] Midfielder is controlling the game.");
    }
}
package com.grokkingsoftwarearchitecture.chapter03.dip.before;

/**
 * A Low-Level Module.
 * * ARCHITECTURE NOTE: This is a concrete implementation of a player. 
 * Because the Coach depends directly on this class, any changes to 
 * the Forward's method names or parameters will immediately break the 
 * Coach class. The dependency arrow points in the wrong direction.
 */
public class Forward {
    public void attack() {
        System.out.println("  [Action] Forward is attacking.");
    }
}
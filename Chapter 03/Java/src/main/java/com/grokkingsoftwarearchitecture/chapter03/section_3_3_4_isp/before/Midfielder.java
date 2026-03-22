package com.grokkingsoftwarearchitecture.chapter03.isp.before;

/**
 * The Polluted Implementation (The Victim of the ISP Violation).
 * * ARCHITECTURE PROBLEM: Because the Midfielder wants to participate in 
 * the TrainingSession, it is FORCED by the compiler to implement methods 
 * it has no business knowing about. 
 * * To make the compiler happy, the developer has to write "dummy" methods 
 * or throw UnsupportedOperationExceptions. This creates "code rot" and sets traps 
 * for other developers who might accidentally call these methods expecting 
 * them to actually do something.
 */
public class Midfielder implements TrainingSession {
    
    /**
     * A valid method for this specific class.
     */
    public void practiceShooting() {
        System.out.println("  [Midfielder] Practicing shooting drills.");
    }

    /**
     * A valid method for this specific class.
     */
    public void practiceTackling() {
        System.out.println("  [Midfielder] Practicing slide tackles.");
    }

    /**
     * 🚨 ARCHITECTURE WARNING: The Midfielder doesn't need this, but the 
     * Fat Interface demands it! We are forced to throw an exception.
     */
    public void practiceDivingSaves() {
        throw new UnsupportedOperationException("Midfielders don't play in the net!");
    }

    /**
     * 🚨 ARCHITECTURE WARNING: Another useless method forced upon us.
     */
    public void practiceHandDistribution() {
        throw new UnsupportedOperationException("Midfielders can't use their hands!");
    }
}
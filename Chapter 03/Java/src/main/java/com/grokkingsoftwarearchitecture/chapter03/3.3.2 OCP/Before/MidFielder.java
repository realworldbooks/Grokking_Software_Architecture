package com.grokkingsoftwarearchitecture.chapter03.ocp.before;

/**
 * ANTI-PATTERN: Violation of the Open/Closed Principle (OCP).
 * * ARCHITECTURE PROBLEM: The Open/Closed Principle states that a class should be 
 * "Open for extension, but Closed for modification." 
 * * Right now, this class is heavily modified every time requirements change. If 
 * the team learns a new play (like "PassToStriker"), we are FORCED to open this 
 * file, modify the Midfielder class, and add another 'else if' block. 
 * * This means every new feature requires altering existing, already-tested code, 
 * which dramatically increases the risk of introducing regressions or bugs.
 */
public class Midfielder {
    
    /**
     * Executes a play based on a hardcoded string. 
     * This creates a fragile, infinitely growing conditional chain.
     * * @param playName The string identifier of the play.
     */
    public void executePlay(String playName) {
        // 🚨 ARCHITECTURE WARNING: This if/else chain will grow forever.
        if ("DribblePastOpponent".equals(playName)) {
            System.out.println("  [Action] Executing a dribble move…");
        } else if ("DefensiveFormation".equals(playName)) {
            System.out.println("  [Action] Getting into defensive position…");
        } else {
            System.out.println("  [Error] Unknown play: " + playName);
        }
    }
}
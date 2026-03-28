package com.grokkingsoftwarearchitecture.chapter03.section_3_3_4_isp.after;

/**
 * The Clean Implementation.
 * * ARCHITECTURE NOTE: Look at how clean this class is now! There are no 
 * UnsupportedOperationExceptions. There is no dead code. The Midfielder simply 
 * signs the FieldPlayerTraining contract and easily fulfills all of its 
 * obligations. The class is now highly cohesive.
 */
public class Midfielder implements FieldPlayerTraining {
    
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
}
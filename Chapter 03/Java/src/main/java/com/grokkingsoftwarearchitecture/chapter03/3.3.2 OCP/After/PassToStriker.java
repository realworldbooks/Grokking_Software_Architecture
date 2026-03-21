package com.grokkingsoftwarearchitecture.chapter03.ocp.after;

/**
 * OCP SOLUTION: Open for Extension.
 * * ARCHITECTURE NOTE: This class proves that our system is "Open for extension." 
 * We added this brand new feature (a new play) simply by creating a new file 
 * and implementing the Play interface. We extended the system's capabilities 
 * without touching any existing code!
 */
public class PassToStriker implements Play {
    public void execute() {
        System.out.println("  [Action] Passing the ball to the striker!");
    }
}
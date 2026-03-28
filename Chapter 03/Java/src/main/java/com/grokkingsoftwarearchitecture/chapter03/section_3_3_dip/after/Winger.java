package com.grokkingsoftwarearchitecture.chapter03.section_3_3_dip.after;

/**
 * DIP SOLUTION: The "Plug-and-Play" Proof.
 * * ARCHITECTURE NOTE: Because the Coach depends on an abstraction, we were 
 * able to create this brand new Winger class and instantly inject it into 
 * the Coach's game plan without altering a single line of the Coach class! 
 * * This is the ultimate goal of software architecture: adding new features 
 * by adding new code, rather than modifying existing code.
 */
public class Winger implements Player {
    public void performAction() {
        System.out.println("  [Action] Winger is running down the sideline.");
    }
}
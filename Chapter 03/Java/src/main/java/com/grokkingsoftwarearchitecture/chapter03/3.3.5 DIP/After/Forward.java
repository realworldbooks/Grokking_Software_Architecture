package com.grokkingsoftwarearchitecture.chapter03.dip.after;

/**
 * A Low-Level Detail.
 * * ARCHITECTURE NOTE: The concrete implementation now depends on the abstraction 
 * (Player). The flow of dependency has been strictly inverted.
 */
public class Forward implements Player {
    public void performAction() {
        System.out.println("  [Action] Forward is attacking.");
    }
}
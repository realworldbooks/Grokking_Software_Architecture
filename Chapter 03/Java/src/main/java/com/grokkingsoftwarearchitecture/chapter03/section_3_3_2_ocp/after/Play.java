package com.grokkingsoftwarearchitecture.chapter03.ocp.after;

/**
 * The Architectural Boundary (The Contract).
 * * ARCHITECTURE NOTE: This interface is the secret to the Open/Closed Principle. 
 * By defining a standard contract that all plays must follow, we create a "seam" 
 * in our architecture. The Midfielder doesn't need to know the details of the play, 
 * it only needs to know that the play has an execute() method.
 */
public interface Play {
    void execute();
}
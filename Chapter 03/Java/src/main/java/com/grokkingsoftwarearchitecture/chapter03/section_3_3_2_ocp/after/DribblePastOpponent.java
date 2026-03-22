package com.grokkingsoftwarearchitecture.chapter03.ocp.after;

/**
 * A concrete implementation of the Play interface.
 */
public class DribblePastOpponent implements Play {
    public void execute() {
        System.out.println("  [Action] Executing a dribble move…");
    }
}
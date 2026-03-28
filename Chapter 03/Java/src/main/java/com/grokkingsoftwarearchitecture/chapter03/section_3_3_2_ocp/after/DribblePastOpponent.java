package com.grokkingsoftwarearchitecture.chapter03.section_3_3_2_ocp.after;

/**
 * A concrete implementation of the Play interface.
 */
public class DribblePastOpponent implements Play {
    public void execute() {
        System.out.println("  [Action] Executing a dribble move…");
    }
}
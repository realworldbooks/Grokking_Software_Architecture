package com.grokkingsoftwarearchitecture.chapter03.section_3_3_2_ocp.after;

/**
 * A concrete implementation of the Play interface.
 */
public class DefensiveFormation implements Play {
    public void execute() {
        System.out.println("  [Action] Getting into defensive position…");
    }
}
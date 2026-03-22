package com.grokkingsoftwarearchitecture.chapter03.isp.after;

/**
 * Interface Composition.
 * * ARCHITECTURE NOTE: The beauty of Interface Segregation is that classes 
 * can just "opt-in" to the behaviors they actually need. Because the Goalie 
 * needs to practice everything, it simply implements BOTH interfaces. 
 * * We accommodated the complex requirements of the Goalie without polluting 
 * the Midfielder's codebase!
 */
public class Goalie implements FieldPlayerTraining, GoalieTraining {
    
    public void practiceShooting() {
        System.out.println("  [Goalie] Practicing goal kicks and long shots.");
    }

    public void practiceTackling() {
        System.out.println("  [Goalie] Practicing 1-on-1 box tackles.");
    }

    public void practiceDivingSaves() {
        System.out.println("  [Goalie] Practicing top-corner diving saves.");
    }

    public void practiceHandDistribution() {
        System.out.println("  [Goalie] Practicing fast break throws.");
    }
}
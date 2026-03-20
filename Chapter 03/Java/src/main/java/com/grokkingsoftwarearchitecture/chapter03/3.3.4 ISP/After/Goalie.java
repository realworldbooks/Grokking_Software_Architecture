package com.grokkingsoftwarearchitecture.chapter03.isp.after;

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
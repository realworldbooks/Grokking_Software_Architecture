package com.grokkingsoftwarearchitecture.chapter03.isp.after;

public class Midfielder implements FieldPlayerTraining {
    public void practiceShooting() {
        System.out.println("  [Midfielder] Practicing shooting drills.");
    }

    public void practiceTackling() {
        System.out.println("  [Midfielder] Practicing slide tackles.");
    }
}
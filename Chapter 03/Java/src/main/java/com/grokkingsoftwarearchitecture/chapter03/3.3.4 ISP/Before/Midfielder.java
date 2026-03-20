package com.grokkingsoftwarearchitecture.chapter03.isp.before;

public class Midfielder implements TrainingSession {
    public void practiceShooting() {
        System.out.println("  [Midfielder] Practicing shooting drills.");
    }

    public void practiceTackling() {
        System.out.println("  [Midfielder] Practicing slide tackles.");
    }

    public void practiceDivingSaves() {
        throw new UnsupportedOperationException("Midfielders don't play in the net!");
    }

    public void practiceHandDistribution() {
        throw new UnsupportedOperationException("Midfielders can't use their hands!");
    }
}
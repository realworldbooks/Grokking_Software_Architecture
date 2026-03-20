package com.grokkingsoftwarearchitecture.chapter03.srp.before;

public class Player {
    public String name;

    public Player(String name) {
        this.name = name;
    }

    // Responsibility 1: Player’s own state/abilities
    public void dribbleBall() {
        System.out.println("  [Action] " + name + " is dribbling the ball down the court.");
    }

    // Responsibility 2: Tactical Logic
    public void determineBestPosition() {
        System.out.println("  [Tactics] Calculating optimal court position for " + name + "...");
    }

    // Responsibility 3: Data Persistence
    public void saveStatsToDatabase() {
        System.out.println("  [Database] Saving " + name + "'s game stats to the database.");
    }
}
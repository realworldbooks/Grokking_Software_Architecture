package com.grokkingsoftwarearchitecture.chapter03.section_3_3_1_srp.before;

/**
 * ANTI-PATTERN: The "God Class" (SRP Violation).
 * * ARCHITECTURE PROBLEM: This class is doing too much. According to the Single 
 * Responsibility Principle, a class should have only one reason to change. 
 * This class currently has THREE:
 * * 1. If the physical rules of the game change (Action).
 * 2. If the AI algorithms change (Tactics).
 * 3. If the database schema or ORM changes (Persistence).
 * * By bundling these together, a simple change to a database connection string 
 * could accidentally break the game's tactical logic. Furthermore, you cannot 
 * easily unit test the tactical logic without also spinning up a database connection.
 */
public class Player {
    public String name;

    public Player(String name) {
        this.name = name;
    }

    /**
     * Responsibility 1: Domain Logic. 
     * (This is the only thing that actually belongs in this class!)
     */
    public void dribbleBall() {
        System.out.println("  [Action] " + name + " is dribbling the ball down the court.");
    }

    /**
     * Responsibility 2: Tactical/AI Logic.
     * (Should be moved to a dedicated engine or service).
     */
    public void determineBestPosition() {
        System.out.println("  [Tactics] Calculating optimal court position for " + name + "...");
    }

    /**
     * Responsibility 3: Infrastructure/Persistence.
     * (Strongly couples the domain model to a specific database technology).
     */
    public void saveStatsToDatabase() {
        System.out.println("  [Database] Saving " + name + "'s game stats to the database.");
    }
}
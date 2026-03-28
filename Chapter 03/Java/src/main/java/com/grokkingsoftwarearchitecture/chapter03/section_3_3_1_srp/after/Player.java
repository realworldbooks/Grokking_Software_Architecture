package com.grokkingsoftwarearchitecture.chapter03.section_3_3_1_srp.after;

/**
 * Responsibility 1: State and Core Actions.
 * * ARCHITECTURE NOTE: By stripping out the database and tactical logic, the Player 
 * class is now highly cohesive. It has only one reason to change: if the fundamental 
 * rules of a player (like adding a 'pass' method or 'stamina' property) change.
 */
public class Player {
    public String name;

    public Player(String name) {
        this.name = name;
    }

    /**
     * Executes a core domain action specific to the player's physical state.
     */
    public void dribbleBall() {
        System.out.println("  [Action] " + name + " is dribbling the ball down the court.");
    }
}
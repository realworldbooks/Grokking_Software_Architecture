package com.grokkingsoftwarearchitecture.chapter03.srp.after;

// Responsibility 1: Manages only the player’s state and actions
public class Player {
    public String name;

    public Player(String name) {
        this.name = name;
    }

    public void dribbleBall() {
        System.out.println("  [Action] " + name + " is dribbling the ball down the court.");
    }
}
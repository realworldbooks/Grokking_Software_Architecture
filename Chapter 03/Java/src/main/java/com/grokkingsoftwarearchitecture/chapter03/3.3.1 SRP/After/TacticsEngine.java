package com.grokkingsoftwarearchitecture.chapter03.srp.after;

// Responsibility 2: Manages only tactical decisions
public class TacticsEngine {
    public void determineBestPosition(Player player) {
        System.out.println("  [Tactics] Calculating optimal court position for " + player.name + "...");
    }
}
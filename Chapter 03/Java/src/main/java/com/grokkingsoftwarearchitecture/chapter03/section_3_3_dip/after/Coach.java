package com.grokkingsoftwarearchitecture.chapter03.dip.after;

import java.util.List;

/**
 * DIP SOLUTION: Constructor Injection and Loose Coupling.
 * * ARCHITECTURE NOTE: The Coach is no longer responsible for building its own 
 * team. The 'new' keyword has been completely removed! 
 * * Instead, the Coach asks for its dependencies through its constructor. This 
 * is called "Dependency Injection." Because the Coach only asks for a list 
 * of 'Player' abstractions, we can hand it literally any combination of players. 
 * * This makes the Coach class infinitely extensible and incredibly easy to unit 
 * test (we can just pass in "Mock" players during testing without spinning up 
 * concrete classes).
 */
public class Coach {
    // The Coach depends strictly on an abstraction!
    private final List<Player> team;

    /**
     * Dependencies are provided from the outside (Constructor Injection).
     */
    public Coach(List<Player> players) {
        this.team = players;
    }

    public void executeGamePlan() {
        for (Player player : team) {
            player.performAction();
        }
    }
}
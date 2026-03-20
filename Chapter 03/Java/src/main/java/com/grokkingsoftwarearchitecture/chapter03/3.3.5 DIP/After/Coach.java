package com.grokkingsoftwarearchitecture.chapter03.dip.after;

import java.util.List;

// High-level module (also depends on abstractions)
public class Coach {
    private final List<Player> team;

    // Dependencies are "injected" via the constructor!
    public Coach(List<Player> players) {
        this.team = players;
    }

    public void executeGamePlan() {
        for (Player player : team) {
            player.performAction();
        }
    }
}
package com.grokkingsoftwarearchitecture.chapter03.ocp.before;

public class Midfielder {
    public void executePlay(String playName) {
        if ("DribblePastOpponent".equals(playName)) {
            System.out.println("  [Action] Executing a dribble move…");
        } else if ("DefensiveFormation".equals(playName)) {
            System.out.println("  [Action] Getting into defensive position…");
        } else {
            System.out.println("  [Error] Unknown play: " + playName);
        }
    }
}
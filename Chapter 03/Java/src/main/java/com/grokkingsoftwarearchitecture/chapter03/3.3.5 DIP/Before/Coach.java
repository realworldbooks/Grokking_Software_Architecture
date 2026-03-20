package com.grokkingsoftwarearchitecture.chapter03.dip.before;

public class Coach {
    private Forward forward;
    private Midfielder midfielder;

    public Coach() {
        forward = new Forward();
        midfielder = new Midfielder();
    }

    public void executeGamePlan() {
        forward.attack();
        midfielder.controlMidfield();
    }
}
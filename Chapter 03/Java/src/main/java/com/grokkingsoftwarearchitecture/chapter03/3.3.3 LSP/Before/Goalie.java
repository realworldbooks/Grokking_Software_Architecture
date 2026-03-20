package com.grokkingsoftwarearchitecture.chapter03.lsp.before;

public class Goalie extends Player {
    @Override
    public void playFieldPosition() {
        System.out.println("  [Goalie] I can't do that! I stay near the net and use my hands.");
    }
}
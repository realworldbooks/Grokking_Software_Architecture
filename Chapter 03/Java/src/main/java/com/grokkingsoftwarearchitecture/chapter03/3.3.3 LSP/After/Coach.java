package com.grokkingsoftwarearchitecture.chapter03.lsp.after;

public class Coach {
    public void directFieldPlay(Player fieldPlayer) {
        System.out.println("  [Coach] Alright player, execute your field assignment!");
        fieldPlayer.playFieldPosition();
    }
}
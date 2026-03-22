package com.grokkingsoftwarearchitecture.chapter04;

// A fake UI layer class to illustrate the bad dependency
public class PresentationLayer {
    private static final PresentationLayer INSTANCE = 
        new PresentationLayer();

    public static PresentationLayer getInstance() {
        return INSTANCE;
    }

    public void updateStatusLabel(String text) {
        System.out.println("[UI UPDATE]: " + text);
    }
}
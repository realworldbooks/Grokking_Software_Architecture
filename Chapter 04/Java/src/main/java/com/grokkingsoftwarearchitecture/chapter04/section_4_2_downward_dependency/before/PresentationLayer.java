package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.before;

/**
 * A fake UI layer class to illustrate the bad dependency.
 * * ARCHITECTURE NOTE:
 * High-level layers should be independent of the implementation 
 * details of lower layers.
 */
public class PresentationLayer {
    private static final PresentationLayer instance = new PresentationLayer();

    public static PresentationLayer getInstance() {
        return instance;
    }

    public void updateStatusLabel(String text) {
        System.out.println("[UI UPDATE]: " + text);
    }
}
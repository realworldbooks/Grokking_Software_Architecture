package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.before;

import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;

// A fake UI layer class to illustrate the bad dependency
public final class PresentationLayer {

    private PresentationLayer() {
        // Private constructor to prevent instantiation
    }

    public static void updateStatusLabel(String text) {
        LogManager.info(PresentationLayer.class, "[UI UPDATE]: {0}", text);
    }
}
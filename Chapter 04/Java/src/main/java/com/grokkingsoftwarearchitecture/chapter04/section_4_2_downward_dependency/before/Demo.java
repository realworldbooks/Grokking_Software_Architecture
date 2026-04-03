package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.before;

import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;

/**
 * The entry point for the Java application.
 */
public class Demo {

    private Demo() {
        // Private constructor to hide the implicit public one
    }

    public static void run() {
        LogManager.info(Demo.class, "--- Running 'Before' (Upward Dep) ---");
        
        SomeRepository beforeRepo = new SomeRepository();
        beforeRepo.updateData(123, "New Data");
        
        LogManager.info(Demo.class, "------------------------------------");
    }
}
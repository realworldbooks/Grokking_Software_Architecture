package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.before;

/**
 * THE EXECUTION LAYER:
 * * ARCHITECTURE NOTE:
 * This class demonstrates a tightly coupled version where a 
 * low-level component is trying to control a high-level one.
 */
public class Demo {
    /**
     * THE STATIC ENTRY POINT:
     * * This satisfies the lab orchestrator's requirement for a 
     * consistent 'run' interface across all project types.
     */
    public static void run() {
        System.out.println("--- Running 'Before Refactoring' (Upward Dependency) ---");
        
        // Normal instantiation of the tightly coupled repository
        SomeRepository beforeRepo = new SomeRepository();
        beforeRepo.updateData(123, "New Data");
        
        System.out.println("---------------------------------------------");
    }
}
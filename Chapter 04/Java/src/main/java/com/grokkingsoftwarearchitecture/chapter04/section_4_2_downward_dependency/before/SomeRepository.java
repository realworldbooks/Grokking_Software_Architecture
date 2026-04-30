package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.before;

/**
 * DataAccessLayer.java
 * * ARCHITECTURAL CRITIQUE:
 * This class represents an "Upward Dependency" violation. A lower 
 * layer (Infrastructure/Data Access) should never know about an 
 * upper layer (UI/Presentation).
 */
public class SomeRepository {
    // This is the violation! A lower layer should not
    // know about an upper layer.
    private PresentationLayer uiLayer = PresentationLayer.getInstance();

    public void updateData(int id, String newData) {
        // Normal print statement as requested
        System.out.println("(Before Refactoring) Saving data to database...");
        
        // VIOLATION! Calling upwards to the UI Layer.
        // This makes the repository impossible to test without 
        // bringing the entire UI with it.
        uiLayer.updateStatusLabel("(Before Refactoring) Data " + id + " Saved!");
    }
}
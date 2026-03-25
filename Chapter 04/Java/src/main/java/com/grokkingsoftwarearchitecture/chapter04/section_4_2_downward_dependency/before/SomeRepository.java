package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.before;

/**
 * ARCHITECTURE WARNING: Upward Dependency Violation.
 * DataAccessLayer.java 
 */
public class SomeRepository {
    // VIOLATION: A lower layer should not know about an upper layer.
    private PresentationLayer _uiLayer = PresentationLayer.getInstance();

    public void updateData(int id, String newData) {
        System.out.println("(Before) Saving data to database...");
        
        // VIOLATION: Calling upwards to the UI Layer
        _uiLayer.updateStatusLabel("(Before) Data " + id + " Saved!");
    }
}
package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.before;

import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;

/**
 * ARCHITECTURE WARNING: Upward Dependency Violation.
 * DataAccessLayer.java 
 */
public class SomeRepository {

    public void updateData(int id, String newData) {
        LogManager.info(SomeRepository.class, "(Before) Saving data to database...data: " + newData);
        
        // VIOLATION: Calling upwards to the UI Layer
        PresentationLayer.updateStatusLabel("(Before) Data " + id + " Saved!");
    }
}
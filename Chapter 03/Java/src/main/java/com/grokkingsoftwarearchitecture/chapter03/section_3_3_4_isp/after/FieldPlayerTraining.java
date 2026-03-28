package com.grokkingsoftwarearchitecture.chapter03.section_3_3_4_isp.after;

/**
 * ISP SOLUTION: Segregated Interfaces.
 * * ARCHITECTURE NOTE: We broke the "Fat Interface" down into highly specific, 
 * role-based contracts. This interface now ONLY contains the methods that 
 * universally apply to anyone playing on the field. Clients are no longer 
 * forced to depend on methods they don't use.
 */
public interface FieldPlayerTraining {
    void practiceShooting();
    void practiceTackling();
}
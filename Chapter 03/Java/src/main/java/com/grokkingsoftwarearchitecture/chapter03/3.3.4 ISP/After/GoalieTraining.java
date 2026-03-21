package com.grokkingsoftwarearchitecture.chapter03.isp.after;

/**
 * ISP SOLUTION: Segregated Interfaces.
 * * ARCHITECTURE NOTE: This interface isolates the highly specialized skills 
 * required only by the Goalie. By keeping this separate, we protect the rest 
 * of the team from having to write dummy implementations for these methods.
 */
public interface GoalieTraining {
    void practiceDivingSaves();
    void practiceHandDistribution();
}
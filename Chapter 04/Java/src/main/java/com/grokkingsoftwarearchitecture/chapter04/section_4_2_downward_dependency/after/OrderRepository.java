package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.after;

/**
 * THE ABSTRACTION (Interface).
 * ARCHITECTURE NOTE: In Java, we don't prefix with 'I'. The 
 * interface is the "pure" name of the role.
 */
public interface OrderRepository {
    void save(Order order);
}
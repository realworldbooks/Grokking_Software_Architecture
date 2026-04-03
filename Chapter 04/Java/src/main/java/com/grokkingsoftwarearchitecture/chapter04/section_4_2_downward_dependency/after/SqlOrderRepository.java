package com.grokkingsoftwarearchitecture.chapter04.section_4_2_downward_dependency.after;

import com.grokkingsoftwarearchitecture.chapter04.shared.LogManager;

/**
 * DATA ACCESS LAYER.
 * Implements the interface.
 */
public class SqlOrderRepository implements OrderRepository {
    @Override
    public void save(Order order) {
        LogManager.info(SqlOrderRepository.class, "(After Refactor) Saving order to SQL...");
    }
}
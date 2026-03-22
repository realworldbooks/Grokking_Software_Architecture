package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.data_access;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain_models.Item;

/**
 * ARCHITECTURE NOTE: The Business Layer depends on this interface,
 * not the implementation. This follows the Dependency Inversion Principle.
 */
public interface ItemRepository {
    Item getById(int id);
}
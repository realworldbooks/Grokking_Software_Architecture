// Chapter 04/Java/src/main/java/com/grokkingsoftwarearchitecture/chapter04/section_4_4_anti_patterns/after_rich_domain/dataaccess/IOrderRepository.java
package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.dataaccess;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domainmodels.Order;

/**
 * Interface for saving an order. In a real app, this would
 * handle database operations.
 *
 * In a traditional 4-layer architecture, the Data Access
 * layer defines its own contracts (interfaces). Higher layers
 * like Business Logic will depend on these abstractions.
 */
public interface IOrderRepository {
    void saveOrder(Order order);
}

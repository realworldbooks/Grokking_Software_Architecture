package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.infrastructure;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.models.Item;
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.interfaces.ItemRepository;

/**
 * DATA ACCESS LAYER: SQL IMPLEMENTATION
 * ARCHITECTURE NOTE: This simulates a database lookup. By fetching the 
 * Item here, we ensure the Business Logic uses the official price 
 * stored in our system, rather than a price sent by the client.
 */
public class SqlItemRepository implements ItemRepository {

    @Override
    public Item getById(int id) {
        System.out.println("  [DB] Fetching official data for Item ID: " + id);

        // In a real app, this would be a SQL query: 
        // SELECT price FROM items WHERE id = ?
        Item item = new Item();
        
        if (id == 1) {
            item.price = 100.0;
        } else if (id == 2) {
            item.price = 50.0;
        } else {
            // Default or fallback for testing
            item.price = 75.0;
        }

        return item;
    }
}
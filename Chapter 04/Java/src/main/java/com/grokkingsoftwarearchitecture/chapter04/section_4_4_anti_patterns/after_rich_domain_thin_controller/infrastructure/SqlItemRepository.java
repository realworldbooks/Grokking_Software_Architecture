package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.infrastructure;

import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.models.Item;
import com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.interfaces.ItemRepository;

/**
 * INFRASTRUCTURE LAYER: SQL IMPLEMENTATION
 * ARCHITECTURE NOTE: This simulates a database lookup. By fetching the 
 * Item here, we ensure the Business Logic uses the official price 
 * stored in our system, rather than a price sent by the client.
 */
public class SqlItemRepository implements ItemRepository {

    @Override
   public Item getById(int itemId) {
        System.out.println("(INFRA) SQL: Fetching official price for Item " + itemId);
        Item item = new Item();
        item.id = itemId;
        item.price = (itemId == 1) ? 100.0 : 50.0;
        return item;
    }
}
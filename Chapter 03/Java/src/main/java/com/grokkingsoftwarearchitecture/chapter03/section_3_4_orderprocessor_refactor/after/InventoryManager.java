package com.grokkingsoftwarearchitecture.chapter03.section_3_4_orderprocessor_refactor.after;

/**
 * SRP SOLUTION: Infrastructure Isolation.
 * * ARCHITECTURE NOTE: If we migrate from Oracle to PostgreSQL, the 
 * billing team and sales team won't even notice, because only this 
 * inventory manager class needs to change.
 */
public class InventoryManager {
    public void updateInventory(Order order) {
        System.out.println("  [Inventory] Updating inventory...");
        // Real database logic to update stock would go here
    }
}
package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain;

/**
 * ARCHITECTURE NOTE: Not every domain model needs complex behavior. 
 * Because the core business rules for this bounded context revolve 
 * around the Order, this Customer class can remain a simple data 
 * entity holding state.
 */
public class Customer {
    public int id;
    public String type; // e.g., "Gold"
    public String email;
}
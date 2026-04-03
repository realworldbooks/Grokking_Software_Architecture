package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.after_rich_domain_thin_controller.domain.models;

/**
 * ARCHITECTURE NOTE: Not every domain model needs complex behavior. 
 * Because the core business rules for this bounded context revolve 
 * around the Order, this Customer class can remain a simple data 
 * entity holding state.
 */
public class Customer {
    private int id;
    private String type; // e.g., "Gold"
    private String email;

    public Customer() {
        // Default constructor
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
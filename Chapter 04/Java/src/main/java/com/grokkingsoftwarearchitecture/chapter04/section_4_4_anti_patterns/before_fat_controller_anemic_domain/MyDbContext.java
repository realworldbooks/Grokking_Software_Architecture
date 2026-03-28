package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;
import java.util.ArrayList;
import java.util.List;

/**
 * ARCHITECTURAL NOTE: The Mock Database Context.
 * This mimics a direct ORM context (like Hibernate/JPA EntityManager), 
 * demonstrating how persistence logic is often leaked directly into the API layer.
 */
public class MyDbContext {
    public List<Customer> customers = List.of(new Customer(1, "Gold", "a@b.com"));
    public List<DbItem> items = List.of(new DbItem(1, "Laptop", 100.0), new DbItem(2, "Mouse", 50.0));
    public List<Order> orders = new ArrayList<>();

    public void saveChanges() { /* Simulates DB commit */ }
    public void close() { /* Simulates closing connection */ }
}
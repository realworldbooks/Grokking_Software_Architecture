package com.grokkingsoftwarearchitecture.chapter02.section_2_4_1_constraints_in_action; 
/**
 * Represents a user entity.
 * This is a simple data-holding class, often called a POCO (Plain Old Java Object)
 * or a DTO (Data Transfer Object). Its job is to represent the structure of our data
 * as it moves between different layers of the application (e.g., from the database
 * to the controller).
 * * ARCHITECTURAL NOTE: Structural Constraints
 * By isolating this model into its own file, we ensure that the shape of our data 
 * is completely decoupled from how it is retrieved or processed.
 */
public class User {
    
    // In Java, we enforce the "required" constraint by making these fields final 
    // and requiring them in the constructor. This prevents developers from 
    // accidentally creating invalid, empty users.
    private final String id;
    private final String name;
    private final String email;

    /**
     * Initializes a new User. All fields are required to enforce the data constraint.
     */
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * The unique identifier for the user.
     */
    public String getId() {
        return id;
    }

    /**
     * The user's full name.
     */
    public String getName() {
        return name;
    }

    /**
     * The user's email address.
     */
    public String getEmail() {
        return email;
    }
}
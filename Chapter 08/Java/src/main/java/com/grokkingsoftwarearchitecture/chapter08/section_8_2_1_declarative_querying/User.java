package com.grokkingsoftwarearchitecture.chapter08.section_8_2_1_declarative_querying;

import jakarta.persistence.*;

/**
 * THE DOMAIN MODEL (Declarative Mapping):
 * * TEACHING NOTE:
 * Notice that we use annotations (@Entity, @Table) to declare what we want.
 * We do not write 'CREATE TABLE' statements. Hibernate will scan this class
 * and automatically build the perfect database schema for us.
 */
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    private int age;
    
    @Column(name = "is_active")
    private boolean isActive;

    // Hibernate requires a no-args constructor
    public User() {}

    public User(String firstName, String lastName, int age, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public boolean isActive() { return isActive; }
}

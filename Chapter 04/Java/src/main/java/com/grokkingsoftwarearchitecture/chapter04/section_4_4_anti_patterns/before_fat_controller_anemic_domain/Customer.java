package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;

public class Customer {
    public int id;
    public String type;
    public String email;
    
    public Customer(int id, String type, String email) { 
        this.id = id; 
        this.type = type; 
        this.email = email; 
    }
}
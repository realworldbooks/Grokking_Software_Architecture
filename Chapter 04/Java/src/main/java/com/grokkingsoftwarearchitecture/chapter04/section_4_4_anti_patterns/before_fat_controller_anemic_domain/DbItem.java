package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;

public class DbItem {
    public int id;
    public String name;
    public double price;
    
    public DbItem(int id, String name, double price) { 
        this.id = id; 
        this.name = name; 
        this.price = price; 
    }
}
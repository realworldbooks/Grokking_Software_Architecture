package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;

import java.util.List;



/**
 * THE ANEMIC DOMAIN MODEL.
 * ARCHITECTURE WARNING: This is just a data container #A.
 * It has no business logic, making it "Anemic" #B.
 */
public class Order {
    public int id;
    public double total;
    public String customerEmail;
}
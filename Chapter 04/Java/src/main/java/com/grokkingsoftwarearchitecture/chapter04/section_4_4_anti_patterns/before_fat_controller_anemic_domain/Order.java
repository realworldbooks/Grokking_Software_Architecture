package com.grokkingsoftwarearchitecture.chapter04.section_4_4_anti_patterns.before_fat_controller_anemic_domain;
/**
 * THE ANEMIC DOMAIN MODEL.
 * ARCHITECTURE WARNING: This is just a data container #A.
 * It has no business logic, making it "Anemic" #B.
 */
public class Order {
    private int id;
    private double total;
    private String customerEmail;

    public Order() {
        // Default constructor
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
}
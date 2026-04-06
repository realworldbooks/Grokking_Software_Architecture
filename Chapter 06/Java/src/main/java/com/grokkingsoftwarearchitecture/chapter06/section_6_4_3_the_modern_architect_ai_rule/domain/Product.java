package com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.domain;

import java.math.BigDecimal;

/**
 * THE DOMAIN MODEL (Entity).
 * Represents the internal business reality (e.g., a Database Table).
 * This should NEVER be sent directly over the HTTP API.
 */
public class Product {
    private String id;
    private String name;
    private boolean isDigital;
    private BigDecimal weightInLbs;
    private BigDecimal price;

    public Product() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isDigital() { return isDigital; }
    public void setDigital(boolean digital) { isDigital = digital; }

    public BigDecimal getWeightInLbs() { return weightInLbs; }
    public void setWeightInLbs(BigDecimal weightInLbs) { this.weightInLbs = weightInLbs; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
package com.grokkingsoftwarearchitecture.chapter02.section_2_3_2_maintainability.after;

import java.math.BigDecimal;

/**
 * 
 * ARCHITECTURAL NOTE: Clean File Separation
 * 
 * By moving our data structures (POCOs) into their own dedicated files,
 * 
 * we make the codebase vastly easier to navigate. If another developer
 * 
 * needs to see the shape of a CartItem, they don't have to hunt through
 * 
 * business logic to find it.
 */
public class CartItem {
    /*
     * 
     * The name of the product.
     */
    private String name;

    /**
     * 
     * The price of a single unit of the product.
     */
    private BigDecimal price;

    public CartItem(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
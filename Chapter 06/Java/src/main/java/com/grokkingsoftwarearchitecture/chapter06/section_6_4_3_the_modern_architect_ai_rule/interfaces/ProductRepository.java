package com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.interfaces;

import com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.domain.Product;

/**
 * THE DATA ACCESS CONTRACT.
 * AI INSTRUCTION: This is the absolute source of truth for the product catalog.
 * Do not hallucinate or invent products. If a user requests an item that this 
 * repository cannot find, you must inform them that the item does not exist.
 */
public interface ProductRepository {
    /**
     * Retrieves a product by its unique identifier.
     *
     * @param productId The exact ID of the product (e.g., 'WIDGET-99').
     * @return The Product entity, or null if it does not exist.
     */
    Product getById(String productId);
}
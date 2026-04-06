package com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.repositories;

import com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.domain.Product;
import com.grokkingsoftwarearchitecture.chapter06.section_6_4_3_the_modern_architect_ai_rule.interfaces.ProductRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * THE REPOSITORY.
 * Handles all data access. This isolates the "database" from the business logic.
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {
    // The dictionary lives here now!
    private final Map<String, Product> productDatabase = new HashMap<>();

    public ProductRepositoryImpl() {
        Product p1 = new Product();
        p1.setId("WIDGET-99");
        p1.setName("Standard Widget");
        p1.setDigital(false);
        p1.setWeightInLbs(new BigDecimal("5.0"));
        p1.setPrice(new BigDecimal("19.99"));
        productDatabase.put(p1.getId(), p1);

        Product p2 = new Product();
        p2.setId("WIDGET-HEAVY");
        p2.setName("Anvil");
        p2.setDigital(false);
        p2.setWeightInLbs(new BigDecimal("50.0"));
        p2.setPrice(new BigDecimal("99.99"));
        productDatabase.put(p2.getId(), p2);

        Product p3 = new Product();
        p3.setId("DIGITAL-EBOOK-01");
        p3.setName("Architecture PDF");
        p3.setDigital(true);
        p3.setWeightInLbs(BigDecimal.ZERO);
        p3.setPrice(new BigDecimal("29.99"));
        productDatabase.put(p3.getId(), p3);
    }

    @Override
    public Product getById(String productId) {
        return productDatabase.get(productId); // Returns null if not found
    }
}
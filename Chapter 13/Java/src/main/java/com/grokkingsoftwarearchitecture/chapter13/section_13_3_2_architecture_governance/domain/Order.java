package com.grokkingsoftwarearchitecture.chapter13.section_13_3_2_architecture_governance.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * The core Order entity. This is the heart of the domain model.
 *
 * ARCHITECTURAL RULE: This class lives in the Domain layer and must
 * NEVER reference anything from the Infrastructure layer (no databases,
 * no HTTP clients, no external services). The Domain layer is the
 * protected core of the system - it contains pure business logic only.
 *
 * Our fitness function (Listing 13.1) enforces this rule automatically
 * in the CI pipeline. If anyone adds a dependency from this class to
 * the Infrastructure layer, the build fails immediately.
 */
public class Order {
    private final UUID id;
    private final String customerName;
    private final double totalAmount;
    private OrderStatus status;
    private final Instant createdAt;

    public Order(String customerName, double totalAmount) {
        this.id = UUID.randomUUID();
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING;
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public String getCustomerName() { return customerName; }
    public double getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }

    /**
     * Transitions the order to the PAID state.
     * This is pure domain logic - no infrastructure involved.
     */
    public void markAsPaid() {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be marked as paid.");
        }
        status = OrderStatus.PAID;
    }

    /**
     * Transitions the order to the SHIPPED state.
     * Again, pure domain logic with zero infrastructure dependencies.
     */
    public void markAsShipped() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("Only paid orders can be shipped.");
        }
        status = OrderStatus.SHIPPED;
    }
}
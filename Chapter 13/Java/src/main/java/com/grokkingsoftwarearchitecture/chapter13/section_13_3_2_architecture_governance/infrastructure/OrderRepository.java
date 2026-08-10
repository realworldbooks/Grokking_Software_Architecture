package com.grokkingsoftwarearchitecture.chapter13.section_13_3_2_architecture_governance.infrastructure;

import com.grokkingsoftwarearchitecture.chapter13.section_13_3_2_architecture_governance.domain.Order;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The repository that persists Order entities to the database.
 *
 * This is the Infrastructure layer's data access implementation.
 * It depends on the Domain layer (Order entity) - which is "below"
 * it in the dependency graph.
 *
 * ARCHITECTURAL RULE: The Domain layer must NEVER reference this
 * class. Our fitness function enforces this boundary automatically.
 */
public class OrderRepository {
    // In-memory store simulating a real database
    private final Map<UUID, Order> store = new ConcurrentHashMap<>();

    /**
     * Persists a new order to the database.
     */
    public Order save(Order order) {
        store.put(order.getId(), order);
        return order;
    }

    /**
     * Retrieves an order by its unique identifier.
     */
    public Optional<Order> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * Retrieves all orders for a given customer.
     */
    public List<Order> findByCustomer(String customerName) {
        return store.values().stream()
                .filter(o -> o.getCustomerName().equals(customerName))
                .toList();
    }
}
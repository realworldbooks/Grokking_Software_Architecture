package com.grokkingsoftwarearchitecture.chapter13.section_13_3_2_architecture_governance.presentation;

import com.grokkingsoftwarearchitecture.chapter13.section_13_3_2_architecture_governance.domain.Order;
import com.grokkingsoftwarearchitecture.chapter13.section_13_3_2_architecture_governance.infrastructure.OrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The HTTP API controller that exposes Order operations to clients.
 *
 * This is the Presentation layer - the outermost ring of our
 * architecture. It depends on the Application/Domain layers below it.
 *
 * ARCHITECTURAL RULE: This class must:
 *   1. Extend BaseController (enforced by fitness function)
 *   2. End with the "Controller" suffix (enforced by fitness function)
 *   3. Reside in the Presentation package (enforced by fitness function)
 *
 * If any of these rules are violated, the CI pipeline fails the build.
 */
public class OrderController extends BaseController {
    private final OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    /**
     * GET /api/order/{id} - Retrieves a single order by ID.
     */
    public Optional<Order> getById(UUID id) {
        return repository.findById(id);
    }

    /**
     * POST /api/order - Creates a new order.
     */
    public Order create(String customerName, double totalAmount) {
        Order order = new Order(customerName, totalAmount);
        return repository.save(order);
    }

    /**
     * GET /api/order/customer/{name} - Retrieves all orders for a customer.
     */
    public List<Order> getByCustomer(String name) {
        return repository.findByCustomer(name);
    }
}
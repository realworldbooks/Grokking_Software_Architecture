/**
 * The repository that persists Order entities to the database.
 *
 * This is the Infrastructure layer's data access implementation.
 * It depends on the Domain layer (Order entity) - which is "below"
 * it in the dependency graph.
 *
 * ARCHITECTURAL RULE: The Domain layer must NEVER reference this
 * module. Our fitness function enforces this boundary automatically.
 */

import { Order } from "../domain/order.js";

/**
 * In-memory store simulating a real database repository.
 */
export class OrderRepository {
  constructor() {
    /** @type {Map<string, Order>} */
    this._store = new Map();
  }

  /** Persists a new order to the (simulated) database. */
  save(order) {
    this._store.set(order.id, order);
    return order;
  }

  /** Retrieves an order by its unique identifier. */
  findById(orderId) {
    return this._store.get(orderId) ?? null;
  }

  /** Retrieves all orders for a given customer. */
  findByCustomer(customerName) {
    return [...this._store.values()].filter(
      (o) => o.customerName === customerName
    );
  }
}
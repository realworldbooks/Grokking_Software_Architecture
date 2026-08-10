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

import { randomUUID } from "crypto";

/** The lifecycle states of an Order. */
export const OrderStatus = Object.freeze({
  PENDING: "pending",
  PAID: "paid",
  SHIPPED: "shipped",
  CANCELLED: "cancelled",
});

/**
 * A customer order in the Shop-Zilla ecosystem.
 * Pure domain logic - no infrastructure involved.
 */
export class Order {
  constructor(customerName, totalAmount) {
    this.id = randomUUID();
    this.customerName = customerName;
    this.totalAmount = totalAmount;
    this.status = OrderStatus.PENDING;
    this.createdAt = new Date();
  }

  /** Transitions the order to the PAID state. */
  markAsPaid() {
    if (this.status !== OrderStatus.PENDING) {
      throw new Error("Only pending orders can be marked as paid.");
    }
    this.status = OrderStatus.PAID;
  }

  /** Transitions the order to the SHIPPED state. */
  markAsShipped() {
    if (this.status !== OrderStatus.PAID) {
      throw new Error("Only paid orders can be shipped.");
    }
    this.status = OrderStatus.SHIPPED;
  }
}
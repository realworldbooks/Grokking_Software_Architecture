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

import { Order } from "../domain/order.js";
import { OrderRepository } from "../infrastructure/orderRepository.js";
import { BaseController } from "./baseController.js";

/**
 * HTTP API controller exposing Order operations to clients.
 */
export class OrderController extends BaseController {
  /** @param {OrderRepository} repository */
  constructor(repository) {
    super();
    this._repository = repository;
  }

  /** GET /api/order/{id} - Retrieves a single order by ID. */
  getById(orderId) {
    return this._repository.findById(orderId);
  }

  /** POST /api/order - Creates a new order. */
  create(customerName, totalAmount) {
    const order = new Order(customerName, totalAmount);
    return this._repository.save(order);
  }

  /** GET /api/order/customer/{name} - Retrieves all orders for a customer. */
  getByCustomer(customerName) {
    return this._repository.findByCustomer(customerName);
  }
}
// Chapter 04/JS/4.4 Anti-Patterns/After-RichDomain/business_logic/OrderService.js
const Customer = require("../domain_models/Customer");
const Item = require("../domain_models/Item");
const Order = require("../domain_models/Order");

/**
 * This service orchestrates the order creation process.
 * It represents the Business Logic Layer.
 *
 * It solves the "Fat Controller" anti-pattern by moving
 * orchestration logic out of the presentation layer and into
 * this dedicated service.
 *
 * Note the downward dependency: this layer depends on both
 * the Domain Models and the Data Access layers.
 */
class OrderService {
    /**
     * @param {IOrderRepository} orderRepository
     * @param {IEmailService} emailService
     */
    constructor(orderRepository, emailService) {
        this._orderRepository = orderRepository;
        this._emailService = emailService;
    }

    createOrder(request) {
        // In a real system, you would fetch these from the DB
        const customer = new Customer(request.customerId, "Jane Doe", true);
        
        // Create the rich domain model
        const order = new Order(customer);

        for (const reqItem of request.items) {
            // Fetch item from DB
            const item = new Item(reqItem.itemId, "Sample Item", 100.0);
            order.addItem(item, reqItem.quantity);
        }

        // Use the data access layer to persist the order
        this._orderRepository.saveOrder(order);

        // Use the data access layer to send a confirmation
        this._emailService.sendOrderConfirmation(order);

        return order;
    }
}

module.exports = OrderService;

// Chapter 04/JavaScript/4.4 Anti-Patterns/After-RichDomain/business_logic/orderService.js

const Order = require('../domain_models/order');

/**
 * THE SERVICE LAYER (Orchestrator)
 * ARCHITECTURE NOTE: This class replaces the massive "God Method" 
 * from the Fat Controller. It doesn't write to the DB, nor does 
 * it calculate math. It simply coordinates the flow of data 
 * between the Data Access layer and the Rich Domain Models.
 */
class OrderService {
    
    /**
     * Dependencies on the Data Access layer below it
     * @param {Object} orderRepo 
     * @param {Object} customerRepo 
     * @param {Object} emailService 
     */
    constructor(orderRepo, customerRepo, emailService) {
        this._orderRepo = orderRepo;
        this._customerRepo = customerRepo;
        this._emailService = emailService;
    }

    /**
     * @param {OrderRequest} request
     * @returns {number} The Order ID
     */
    createOrder(request) {
        // 1. Fetch data from lower layer
        const customer = this._customerRepo.getById(request.customerId);
        if (!customer) {
            throw new Error("Not found.");
        }

        // 2. Instantiate the Rich Domain Model
        const order = new Order(customer.email);

        // 3. Delegate business logic to the Rich Model
        for (const item of request.items) {
            // The service doesn't care about discount rules; 
            // the Order model handles that internally.
            order.addItem(item, customer);
        }

        // 4. Send the updated model back down to Data Access
        this._orderRepo.save(order);
        this._emailService.send(
            order.customerEmail, "Confirmed!", "Success."
        );

        return order.id;
    }
}

module.exports = OrderService;
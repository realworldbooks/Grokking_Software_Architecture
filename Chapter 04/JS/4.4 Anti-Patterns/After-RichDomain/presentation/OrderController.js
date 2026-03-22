// Chapter 04/JS/4.4 Anti-Patterns/After-RichDomain/presentation/OrderController.js
const OrderRequest = require("../business_logic/OrderRequest");

/**
 * The Presentation Layer. In a web app, this would be the
 * API endpoint that receives HTTP requests.
 *
 * This controller is now "Thin". Its only job is to translate
 * the incoming request into a DTO, delegate the work to the
 * business layer (OrderService), and return a response. It
 * contains no business logic itself.
 */
class OrderController {
    constructor(orderService) {
        this._orderService = orderService;
    }

    createOrder(rawRequest) {
        /**
         * Simulates handling an HTTP POST request.
         * 'rawRequest' would be the JSON body.
         */
        console.log("---");
        console.log("Controller: Received request.");
        
        // Translate raw request to a DTO
        const orderRequest = new OrderRequest(
            rawRequest.customerId,
            rawRequest.items
        );

        // Delegate to the business logic layer
        const order = this._orderService.createOrder(orderRequest);

        // Return a response (e.g., HTTP 201 Created)
        console.log("Controller: Responding with success.");
        console.log("---");
        return { orderId: order.getCustomer().id, total: order.totalPrice };
    }
}

module.exports = OrderController;

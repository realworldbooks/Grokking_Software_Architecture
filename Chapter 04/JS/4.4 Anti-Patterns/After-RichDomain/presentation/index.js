// Chapter 04/JS/4.4 Anti-Patterns/After-RichDomain/presentation/index.js
// This is the "Composition Root" of the application.
// Its primary responsibility is to create and wire together the
// various components of the system.

const { OrderRepository } = require("../data_access/Repositories");
const { SmtpEmailService } = require("../data_access/EmailService");
const OrderService = require("../business_logic/OrderService");
const OrderController = require("./OrderController");

function main() {
    console.log("App starting...");

    // 1. Create Data Access instances (concrete implementations)
    const orderRepo = new OrderRepository();
    const emailService = new SmtpEmailService();

    // 2. Create Business Logic instance, injecting dependencies
    const orderService = new OrderService(orderRepo, emailService);

    // 3. Create Presentation instance, injecting dependencies
    const orderController = new OrderController(orderService);

    // --- Simulate an incoming web request ---
    // This would typically come from a web framework like Express
    const simulatedRequest = {
        customerId: 456,
        items: [
            { itemId: 3, quantity: 1 },
            { itemId: 4, quantity: 5 }
        ]
    };
    
    // 4. Trigger the application flow
    orderController.createOrder(simulatedRequest);
    
    console.log("App finished.");
}

main();

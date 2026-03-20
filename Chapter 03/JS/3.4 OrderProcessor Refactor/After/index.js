const Order = require('./order');
const { OrderValidator, PaymentService, InventoryManager, NotificationService } = require('./services');
const OrderService = require('./orderService');

console.log("=== Chapter 3: Order Processor (AFTER) ===");
console.log("A coordinator class delegates to focused services...\n");

const order = new Order(["Book", "Pen"], 25.50, "customer@example.com");

const service = new OrderService(
    new OrderValidator(),
    new PaymentService(),
    new InventoryManager(),
    new NotificationService()
);

const result = service.processOrder(order);

console.log(`\nRESULT: ${result}`);
console.log("==========================================\n");
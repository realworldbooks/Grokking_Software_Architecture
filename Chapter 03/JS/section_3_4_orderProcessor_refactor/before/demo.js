const Order = require('./order');
const OrderProcessor = require('./orderProcessor');

console.log("=== Chapter 3: Order Processor (BEFORE) ===");
console.log("One massive class handles everything...\n");

const order = new Order(["Book", "Pen"], 25.50, "customer@example.com");
const processor = new OrderProcessor();
const result = processor.process(order);

console.log(`\nRESULT: ${result}`);
console.log("===========================================\n");
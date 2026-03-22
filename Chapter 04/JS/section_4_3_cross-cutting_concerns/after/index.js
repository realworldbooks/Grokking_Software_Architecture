const Order = require('./order');
const OrderService = require('./orderService');
const FileLogger = require('./fileLogger');

function main() {
    console.log("--- Running 'After' (Injected Logger) ---");
    
    // Dependencies are created and injected at the start
    const logger = new FileLogger();
    const service = new OrderService(logger);
    
    service.saveOrder(new Order());
    console.log("-----------------------------------------");
}
main();
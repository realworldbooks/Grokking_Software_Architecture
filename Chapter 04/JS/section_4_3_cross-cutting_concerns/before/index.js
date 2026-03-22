const Order = require('./order');
const OrderService = require('./orderService');

function main() {
    console.log("--- Running 'Before' (Static Logger) ---");
    
    const beforeService = new OrderService();
    beforeService.saveOrder(new Order());
    
    console.log("-----------------------------------------");
}

main();
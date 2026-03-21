const Order = require('./order');
const OrderService = require('./orderService');
const SqlOrderRepository = require('./sqlOrderRepository');

/**
 * THE COMPOSITION ROOT.
 * This is where we "wire" the concrete implementation 
 * into the high-level service.
 */
function main() {
    console.log("--- Running 'After' (Downward Dep) ---");
    
    const afterRepo = new SqlOrderRepository();
    const afterService = new OrderService(afterRepo);
    
    afterService.saveOrder(new Order());
    console.log("--------------------------------------");
}

main();
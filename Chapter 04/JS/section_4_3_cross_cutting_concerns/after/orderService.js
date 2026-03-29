/**
 * 2. THE CLASS "ASKS" FOR THE DEPENDENCY.
 * ARCHITECTURE NOTE: JS uses Duck Typing. As long as the 
 * injected object has a .log() method, the service is happy.
 */
class OrderService {
    constructor(logger) {
        this.logger = logger;
    }

    saveOrder(order) {
        // 3. Use the abstraction (protocol)
        this.logger.log("Saving order...");
        console.log("(AFTER_SERVICE) Order saved.");
    }
}
module.exports = OrderService;
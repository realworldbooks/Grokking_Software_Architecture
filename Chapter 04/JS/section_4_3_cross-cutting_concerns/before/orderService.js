const StaticFileLogger = require('./staticFileLogger');

/**
 * ARCHITECTURE WARNING: Rigid Dependency.
 * The service is tightly coupled to the specific logger 
 * implementation. Swapping this for a cloud logger would 
 * require modifying this file.
 */
class OrderService {
    saveOrder(order) {
        // 🚨 VIOLATION: Hardcoded reference to a static utility.
        StaticFileLogger.log("Saving order...");
        console.log("(BEFORE_SERVICE) Order saved.");
    }
}

module.exports = OrderService;
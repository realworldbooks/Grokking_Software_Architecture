const UserDataService = require('./userDataService');

/**
 * ANTI-PATTERN: The Tightly Coupled Client.
 * * ARCHITECTURE PROBLEM: Because the UserDataService is too granular, this client 
 * class is forced to take on the responsibility of orchestrating the data gathering. 
 * * The generator now knows WAY too much about the internal structure of the data. 
 * It knows that it has to fetch the user, then fetch the orders, and then loop 
 * through the orders to calculate a total. This is a severe violation of encapsulation.
 */
class UserReportGenerator {
    constructor() {
        this.dataService = new UserDataService();
    }

    generateReport(userId) {
        
        // 🚨 ARCHITECTURE WARNING: High temporal coupling. The client has to 
        // call these specific methods in a specific sequence to get what it needs.
        const name = this.dataService.getUserName(userId);
        const email = this.dataService.getUserEmail(userId);
        const orders = this.dataService.getUserOrderIds(userId);

        let totalSpent = 0;
        
        // 🚨 ARCHITECTURE WARNING: Feature Envy / Chatty Execution.
        // The client is forced to loop through orders and request totals one by one.
        // It is doing the heavy lifting that the service should be doing for it!
        for (const orderId of orders) {
            totalSpent += this.dataService.getOrderTotal(orderId);
        }

        return `User Report for ${name} (${email}) - Total Spent: $${totalSpent.toFixed(2)}`;
    }
}

module.exports = UserReportGenerator;
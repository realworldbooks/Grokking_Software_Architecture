const UserDataService = require('./userDataService');

class UserReportGenerator {
    constructor() {
        this.dataService = new UserDataService();
    }

    generateReport(userId) {
        const name = this.dataService.getUserName(userId);
        const email = this.dataService.getUserEmail(userId);
        const orders = this.dataService.getUserOrderIds(userId);

        let totalSpent = 0;
        for (const orderId of orders) {
            totalSpent += this.dataService.getOrderTotal(orderId);
        }

        return `User Report for ${name} (${email}) - Total Spent: $${totalSpent.toFixed(2)}`;
    }
}

module.exports = UserReportGenerator;
const UserDataService = require('./userDataService');

/**
 * THE SOLUTION: The Loosely Coupled Client.
 * * ARCHITECTURE NOTE: Look at how clean and simple the client has become.
 * It no longer suffers from "Feature Envy"—it doesn't have to loop through 
 * orders, request individual totals, or perform its own math.
 * * It simply asks the service for the completed report data and formats it. 
 * If the underlying database schema or the way a "totalSpent" is calculated 
 * changes, this client class will not require a single line of code to be modified.
 */
class UserReportGenerator {
    constructor() {
        this.dataService = new UserDataService();
    }

    /**
     * Generates a formatted string report for a user using a single service call.
     * @param {number} userId - The ID of the user.
     * @returns {string} The formatted report.
     */
    generateReport(userId) {
        // A single, clean call replaces multiple chatty calls.
        const report = this.dataService.getUserReport(userId);
        
        return `User Report for ${report.name} (${report.email}) - Total Spent: $${report.totalSpent.toFixed(2)}`;
    }
}

module.exports = UserReportGenerator;
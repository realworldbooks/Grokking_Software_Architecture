const UserDataService = require('./userDataService');

class UserReportGenerator {
    constructor() {
        this.dataService = new UserDataService();
    }

    generateReport(userId) {
        const report = this.dataService.getUserReport(userId);
        return `User Report for ${report.name} (${report.email}) - Total Spent: $${report.totalSpent.toFixed(2)}`;
    }
}

module.exports = UserReportGenerator;
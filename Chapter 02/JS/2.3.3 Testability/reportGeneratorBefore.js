const DatabaseConnection = require('./databaseConnection');

class ReportGeneratorBefore {
    constructor() {
        this.dbConnection = new DatabaseConnection("live_connection_string");
    }
    generate(reportName) {
        const data = this.dbConnection.getData(reportName);
        return `Report '${reportName}' generated with ${data.length} rows.`;
    }
}

module.exports = ReportGeneratorBefore;
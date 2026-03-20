class ReportGeneratorAfter {
    constructor(dbConnection) {
        this.dbConnection = dbConnection;
    }
    generate(reportName) {
        const data = this.dbConnection.getData(reportName);
        return `Report '${reportName}' generated with ${data.length} rows.`;
    }
}

module.exports = ReportGeneratorAfter;
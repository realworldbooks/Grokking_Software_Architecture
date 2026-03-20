package com.architecturebook.chapter2.testability;

import java.util.List;

public class ReportGeneratorBefore {
    private RealDatabaseConnection dbConnection;

    public ReportGeneratorBefore() {
        this.dbConnection = new RealDatabaseConnection("live_connection_string");
    }

    public String generate(String reportName) {
        List<String> data = dbConnection.getData(reportName);
        return String.format("Report '%s' generated with %d rows.", reportName, data.size());
    }
}
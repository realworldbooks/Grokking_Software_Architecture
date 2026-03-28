package com.grokkingsoftwarearchitecture.chapter02.section_2_3_3_testability;

import java.util.List;

public class ReportGeneratorAfter {
    private DatabaseConnection dbConnection;

    public ReportGeneratorAfter(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public String generate(String reportName) {
        List<String> data = dbConnection.getData(reportName);
        return String.format("Report '%s' generated with %d rows.", reportName, data.size());
    }
}
package com.grokkingsoftwarearchitecture.chapter02.constraintsinaction;

public class ExportController {
    private final Database db = new Database();

    // Simulating a Spring Boot or Servlet Endpoint
    public void exportUserData(String userId) {
        try {
            User userData = db.fetchUserData(userId).join();

            if (userData == null) {
                System.out.println("  [HTTP 404] User not found.");
                return;
            }

            // Simple CSV conversion
            String headers = "id,name,email\n";
            String csvRow = String.format("%s,%s,%s\n", userData.id, userData.name, userData.email);
            String csvData = headers + csvRow;

            // Simulating HTTP Response
            System.out.println("  [HTTP 200] OK");
            System.out.println("  [Headers] Content-Type: text/csv");
            System.out.println("  [Headers] Content-Disposition: attachment; filename=\"user_data_" + userId + ".csv\"");
            System.out.println("\n--- File Body ---");
            System.out.print(csvData);
            System.out.println("-----------------");

        } catch (Exception e) {
            System.out.println("  [HTTP 500] Export failed: " + e.getMessage());
        }
    }
}
package com.grokkingsoftwarearchitecture.chapter02.section_2_4_1_constraints_in_action;
/**
 * Simulates a "Controller" in a web framework like Spring Boot.
 * Its primary responsibility is to handle incoming web requests, orchestrate the
 * necessary business logic, and then format and return a proper web response.
 */
public class ExportController {
    // A real controller would use Dependency Injection to get service instances, 
    // similar to our Testability example.
    private final Database db = new Database();

    /**
     * Simulates handling a `GET /export-user-data` request.
     * This method demonstrates how architectural constraints (both technical and business)
     * dictate the flow and design of the code.
     * * @param userId The ID of the user to export.
     */
    public void exportUserDataAsync(String userId) {
        try {
            // 1. ORCHESTRATION: The controller calls other services to get the data.
            // We use .join() here to mimic C#'s `await` keyword, pausing execution 
            // until the simulated database finishes its asynchronous work.
            User userData = db.fetchUserDataAsync(userId).join();

            // 2. BUSINESS CONSTRAINT: Handle the case where the user does not exist.
            // The business rule is "if a user is not found, the system should report it clearly."
            // The technical implementation is to return an HTTP 404 Not Found status.
            if (userData == null) {
                System.out.println("  [HTTP 404] User not found.");
                return; // Halt execution, enforcing the constraint.
            }

            // 3. TECHNICAL CONSTRAINT: The data must be formatted according to a specific
            //    file format (CSV in this case). The controller is responsible for
            //    ensuring the output matches the required format.
            String headers = "id,name,email\n";
            String csvRow = String.format("%s,%s,%s\n", userData.getId(), userData.getName(), userData.getEmail());
            String csvData = headers + csvRow;

            // 4. TECHNICAL CONSTRAINT: The response must adhere to the HTTP protocol.
            //    This includes setting the correct status code (200 OK for success) and
            //    response headers (`Content-Type`, `Content-Disposition`) so the client
            //    (e.g., a web browser) knows how to handle the response (e.g., prompt a file download).
            System.out.println("  [HTTP 200] OK");
            System.out.println("  [Headers] Content-Type: text/csv");
            System.out.println("  [Headers] Content-Disposition: attachment; filename=\"user_data_" + userId + ".csv\"");
            System.out.println("\n--- File Body ---");
            System.out.print(csvData);
            System.out.println("-----------------");
        } catch (Exception ex) {
            // 5. BUSINESS/TECHNICAL CONSTRAINT: Handle unexpected errors gracefully.
            // If something goes wrong, the system shouldn't crash. It should catch the
            // exception and return a generic server error (HTTP 500) to the client.
            System.out.println("  [HTTP 500] Export failed: " + ex.getMessage());
        }
    }
}
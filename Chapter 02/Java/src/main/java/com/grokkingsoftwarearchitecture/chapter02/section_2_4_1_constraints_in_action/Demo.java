package com.grokkingsoftwarearchitecture.chapter02.section_2_4_1_constraints_in_action;
/**
 * This class acts as a simple simulator or test harness for the ExportController.
 * It demonstrates how the controller responds to different requests, allowing us
 * to see the architectural constraints in action.
 */
public class Demo {
    public static void run() {
        System.out.println("--- Constraints In Action Example ---");

        // ARCHITECTURAL NOTE: Notice how we are only interacting with the Controller.
        // We do not talk to the Database directly from the Demo, respecting the layer boundaries.
        ExportController controller = new ExportController();

        // SCENARIO 1: A valid request for an existing user.
        // We expect the controller to find the user and return a CSV file
        // with an HTTP 200 OK status.
        System.out.println("\n[SCENARIO 1: Simulating GET /export-user-data for a valid user]");
        controller.exportUserDataAsync("User123");

        // SCENARIO 2: A request for a user who does not exist.
        // We expect the controller to handle this business constraint gracefully
        // by returning an HTTP 404 Not Found status.
        System.out.println("\n[SCENARIO 2: Simulating GET /export-user-data for a non-existent user]");
        controller.exportUserDataAsync("UnknownUser");

        System.out.println("\n-------------------------------------\n");
    }
}
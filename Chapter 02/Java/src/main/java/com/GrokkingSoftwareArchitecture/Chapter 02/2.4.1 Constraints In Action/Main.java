package com.architecturebook.chapter2.constraintsinaction;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chapter 2: Constraints In Action Example ===\n");

        ExportController controller = new ExportController();

        System.out.println("[Simulating GET /export-user-data for User123]");
        controller.exportUserData("User123");

        System.out.println("\n[Simulating GET /export-user-data for UnknownUser]");
        controller.exportUserData("UnknownUser");

        System.out.println("\n==============================================");
    }
}
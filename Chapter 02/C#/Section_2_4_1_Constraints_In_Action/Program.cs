using System;
using System.Threading.Tasks;

namespace Chapter02.ConstraintsInAction;

/// <summary>
/// This class acts as a simple simulator or test harness for the ExportController.
/// It demonstrates how the controller responds to different requests, allowing us
/// to see the architectural constraints in action.
/// </summary>
public static class ConstraintsDemo
{
    public static async Task Run()
    {
        Console.WriteLine("--- Constraints In Action Example ---");

        var controller = new ExportController();

        // SCENARIO 1: A valid request for an existing user.
        // We expect the controller to find the user and return a CSV file
        // with an HTTP 200 OK status.
        Console.WriteLine("\\n[SCENARIO 1: Simulating GET /export-user-data for a valid user]");
        await controller.ExportUserDataAsync("User123");

        // SCENARIO 2: A request for a user who does not exist.
        // We expect the controller to handle this business constraint gracefully
        // by returning an HTTP 404 Not Found status.
        Console.WriteLine("\\n[SCENARIO 2: Simulating GET /export-user-data for a non-existent user]");
        await controller.ExportUserDataAsync("UnknownUser");

        Console.WriteLine("\\n-------------------------------------\\n");
    }
}
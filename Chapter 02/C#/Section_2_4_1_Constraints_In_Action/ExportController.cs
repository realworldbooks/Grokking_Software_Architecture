using System;
using System.Threading.Tasks;

namespace Chapter02.ConstraintsInAction;

/// <summary>
/// Simulates a "Controller" in a web framework like ASP.NET Core.
/// Its primary responsibility is to handle incoming web requests, orchestrate the
/// necessary business logic, and then format and return a proper web response.
/// </summary>
public class ExportController
{
    // A real controller would use Dependency Injection to get service instances.
    private readonly Database _db = new();

    /// <summary>
    /// Simulates handling a `GET /export-user-data` request.
    /// This method demonstrates how architectural constraints (both technical and business)
    /// dictate the flow and design of the code.
    /// </summary>
    /// <param name="userId">The ID of the user to export.</param>
    public async Task ExportUserDataAsync(string userId)
    {
        try
        {
            // 1. ORCHESTRATION: The controller calls other services to get the data.
            var userData = await _db.FetchUserDataAsync(userId);

            // 2. BUSINESS CONSTRAINT: Handle the case where the user does not exist.
            // The business rule is "if a user is not found, the system should report it clearly."
            // The technical implementation is to return an HTTP 404 Not Found status.
            if (userData == null)
            {
                Console.WriteLine("  [HTTP 404] User not found.");
                return;
            }

            // 3. TECHNICAL CONSTRAINT: The data must be formatted according to a specific
            //    file format (CSV in this case). The controller is responsible for
            //    ensuring the output matches the required format.
            var headers = "id,name,email\\n";
            var csvRow = $"{userData.Id},{userData.Name},{userData.Email}\\n";
            var csvData = headers + csvRow;

            // 4. TECHNICAL CONSTRAINT: The response must adhere to the HTTP protocol.
            //    This includes setting the correct status code (200 OK for success) and
            //    response headers (`Content-Type`, `Content-Disposition`) so the client
            //    (e.g., a web browser) knows how to handle the response (e.g., prompt a file download).
            Console.WriteLine("  [HTTP 200] OK");
            Console.WriteLine("  [Headers] Content-Type: text/csv");
            Console.WriteLine($"  [Headers] Content-Disposition: attachment; filename=\"user_data_{userId}.csv\"");
            Console.WriteLine("\\n--- File Body ---");
            Console.Write(csvData);
            Console.WriteLine("-----------------");
        }
        catch (Exception ex)
        {
            // 5. BUSINESS/TECHNICAL CONSTRAINT: Handle unexpected errors gracefully.
            // If something goes wrong, the system shouldn't crash. It should catch the
            // exception and return a generic server error (HTTP 500) to the client.
            Console.WriteLine($"  [HTTP 500] Export failed: {ex.Message}");
        }
    }
}
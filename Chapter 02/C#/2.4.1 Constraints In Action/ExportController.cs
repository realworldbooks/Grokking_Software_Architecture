using System;
using System.Threading.Tasks;

namespace Chapter02.ConstraintsInAction;

public class ExportController
{
    private readonly Database _db = new();

    // Simulating an ASP.NET Endpoint
    public async Task ExportUserDataAsync(string userId)
    {
        try
        {
            var userData = await _db.FetchUserDataAsync(userId);

            if (userData == null)
            {
                Console.WriteLine("  [HTTP 404] User not found.");
                return;
            }

            // Simple CSV conversion
            var headers = "id,name,email\n";
            var csvRow = $"{userData.Id},{userData.Name},{userData.Email}\n";
            var csvData = headers + csvRow;

            // Simulating HTTP Response
            Console.WriteLine("  [HTTP 200] OK");
            Console.WriteLine("  [Headers] Content-Type: text/csv");
            Console.WriteLine($"  [Headers] Content-Disposition: attachment; filename=\"user_data_{userId}.csv\"");
            Console.WriteLine("\n--- File Body ---");
            Console.Write(csvData);
            Console.WriteLine("-----------------");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"  [HTTP 500] Export failed: {ex.Message}");
        }
    }
}
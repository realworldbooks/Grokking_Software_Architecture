using System.Threading.Tasks;

namespace Chapter02.ConstraintsInAction;

/// <summary>
/// Simulates a Data Access Layer (DAL) or "Service" class.
/// Its single responsibility is to handle all interactions with the database.
/// This separation of concerns means that if we were to change our database technology
/// (e.g., from SQL Server to MongoDB), this is the only class we would need to modify.
/// The `ExportController` would remain unchanged.
/// </summary>
public class Database
{
    /// <summary>
    /// Fetches a user's data from the database.
    /// </summary>
    /// <param name="userId">The ID of the user to fetch.</param>
    /// <returns>
    /// A `User` object if the user is found; otherwise, `null`.
    /// Returning nullable `User?` is an explicit design choice. It forces the
    /// calling code (the controller) to acknowledge and handle the possibility
    /// that the user may not exist, which is a crucial business constraint.
    /// </returns>
    public Task<User?> FetchUserDataAsync(string userId)
    {
        // Simulating an asynchronous database call.
        if (userId == "User123")
        {
            return Task.FromResult<User?>(new User 
            { 
                Id = "User123", 
                Name = "Alice", 
                Email = "alice@example.com" 
            });
        }
        // If the user is not found, we return null to signal this to the caller.
        return Task.FromResult<User?>(null);
    }
}
using System.Threading.Tasks;

namespace Chapter02.ConstraintsInAction;

public class Database
{
    public Task<User?> FetchUserDataAsync(string userId)
    {
        // Simulating a database call
        if (userId == "User123")
        {
            return Task.FromResult<User?>(new User 
            { 
                Id = "User123", 
                Name = "Alice", 
                Email = "alice@example.com" 
            });
        }
        return Task.FromResult<User?>(null);
    }
}
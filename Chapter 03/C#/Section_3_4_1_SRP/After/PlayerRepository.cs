using System;

namespace Chapter03.SRP.After;

/// <summary>
/// Responsibility 3: Data Persistence.
/// 
/// ARCHITECTURE NOTE: This class handles all database infrastructure. If we decide 
/// to migrate from a SQL database to a NoSQL database, or if our ORM framework 
/// changes, this is the *only* class that needs to be updated. The Player and 
/// Tactics classes remain completely untouched.
/// </summary>
public class PlayerRepository
{
    /// <summary>
    /// Persists the player's statistics to the underlying data store.
    /// </summary>
    /// <param name="player">The player whose stats need saving.</param>
    public void SaveStats(Player player) 
    {
        Console.WriteLine($"  [Database] Saving {player.Name}'s game stats to the database.");
    }
}
using System;

namespace Chapter03.SRP.After;

// Responsibility 3: Manages only data saving
public class PlayerRepository
{
    public void SaveStats(Player player) 
    {
        Console.WriteLine($"  [Database] Saving {player.Name}'s game stats to the database.");
    }
}
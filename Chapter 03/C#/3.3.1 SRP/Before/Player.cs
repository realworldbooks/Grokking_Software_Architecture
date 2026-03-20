using System;

namespace Chapter03.SRP.Before;

public class Player
{
    public required string Name { get; set; }

    // Responsibility 1: Player’s own state/abilities
    public void DribbleBall() 
    {
        Console.WriteLine($"  [Action] {Name} is dribbling the ball down the court.");
    }

    // Responsibility 2: Tactical Logic
    public void DetermineBestPosition() 
    {
        Console.WriteLine($"  [Tactics] Calculating optimal court position for {Name}...");
    }

    // Responsibility 3: Data Persistence
    public void SaveStatsToDatabase()
    {
        Console.WriteLine($"  [Database] Saving {Name}'s game stats to the database.");
    }
}
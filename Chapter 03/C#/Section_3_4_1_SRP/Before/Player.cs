using System;

namespace Chapter03.SRP.Before;

/// <summary>
/// ANTI-PATTERN: The "God Class" (SRP Violation).
/// 
/// ARCHITECTURE PROBLEM: This class is doing too much. According to the Single 
/// Responsibility Principle, a class should have only one reason to change. 
/// This class currently has THREE:
/// 
/// 1. If the physical rules of the game change (Action).
/// 2. If the AI algorithms change (Tactics).
/// 3. If the database schema or ORM changes (Persistence).
/// 
/// By bundling these together, a simple change to a database connection string 
/// could accidentally break the game's tactical logic. Furthermore, you cannot 
/// easily unit test the tactical logic without also spinning up a database connection.
/// </summary>
public class Player
{
    public required string Name { get; set; }

    /// <summary>
    /// Responsibility 1: Domain Logic. 
    /// (This is the only thing that actually belongs in this class!)
    /// </summary>
    public void DribbleBall() 
    {
        Console.WriteLine($"  [Action] {Name} is dribbling the ball down the court.");
    }

    /// <summary>
    /// Responsibility 2: Tactical/AI Logic.
    /// (Should be moved to a dedicated engine or service).
    /// </summary>
    public void DetermineBestPosition() 
    {
        Console.WriteLine($"  [Tactics] Calculating optimal court position for {Name}...");
    }

    /// <summary>
    /// Responsibility 3: Infrastructure/Persistence.
    /// (Strongly couples the domain model to a specific database technology).
    /// </summary>
    public void SaveStatsToDatabase()
    {
        Console.WriteLine($"  [Database] Saving {Name}'s game stats to the database.");
    }
}
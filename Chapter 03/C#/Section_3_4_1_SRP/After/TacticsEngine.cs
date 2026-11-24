using System;

namespace Chapter03.SRP.After;

/// <summary>
/// Responsibility 2: Tactical / AI Logic.
/// 
/// ARCHITECTURE NOTE: Separating tactics into its own engine means we can 
/// easily swap out algorithms (e.g., switching from "Aggressive" to "Defensive" 
/// tactics) without ever touching the Player class. It also makes this complex 
/// logic incredibly easy to unit test in isolation.
/// </summary>
public class TacticsEngine
{
    /// <summary>
    /// Analyzes the game state to determine the optimal position for a specific player.
    /// </summary>
    /// <param name="player">The player being analyzed.</param>
    public void DetermineBestPosition(Player player) 
    {
        Console.WriteLine($"  [Tactics] Calculating optimal court position for {player.Name}...");
    }
}
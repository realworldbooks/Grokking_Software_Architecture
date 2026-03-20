using System;

namespace Chapter03.SRP.After;

// Responsibility 2: Manages only tactical decisions
public class TacticsEngine
{
    public void DetermineBestPosition(Player player) 
    {
        Console.WriteLine($"  [Tactics] Calculating optimal court position for {player.Name}...");
    }
}
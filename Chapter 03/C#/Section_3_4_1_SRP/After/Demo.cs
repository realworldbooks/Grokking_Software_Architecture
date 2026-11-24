using System;

namespace Chapter03.SRP.After;

public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("=== Chapter 3: SRP (AFTER) ===");
        Console.WriteLine("Responsibilities are cleanly delegated to specific classes!\n");

        var player = new Player { Name = "Alex" };
        var tactics = new TacticsEngine();
        var repository = new PlayerRepository();
        
        player.DribbleBall();
        tactics.DetermineBestPosition(player);
        repository.SaveStats(player);

        Console.WriteLine("\n===============================\n");
    }
}
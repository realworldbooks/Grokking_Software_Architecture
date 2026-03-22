using System;

namespace Chapter03.SRP.Before;

public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("=== Chapter 3: SRP (BEFORE) ===");
        Console.WriteLine("The Player class is doing way too much work!\n");

        var player = new Player { Name = "Alex" };
        
        player.DribbleBall();
        player.DetermineBestPosition();
        player.SaveStatsToDatabase();

        Console.WriteLine("\n===============================\n");
    }
}
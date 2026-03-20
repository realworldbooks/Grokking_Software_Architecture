using System;

namespace Chapter03.ISP.Before;

public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("=== Chapter 3: ISP (BEFORE) ===");
        Console.WriteLine("Midfielder is forced to implement Goalie methods!\n");

        ITrainingSession player = new Midfielder();
        
        player.PracticeShooting();
        player.PracticeTackling();

        try 
        {
            player.PracticeDivingSaves(); // This will crash!
        }
        catch(Exception ex)
        {
            Console.WriteLine($"[ERROR] {ex.Message}");
        }

        Console.WriteLine("\n===============================\n");
    }
}
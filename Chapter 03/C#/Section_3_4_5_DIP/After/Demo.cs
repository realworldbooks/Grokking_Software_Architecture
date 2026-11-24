using System;
using System.Collections.Generic;

namespace Chapter03.DIP.After;

public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("=== Chapter 3: DIP (AFTER) ===");
        Console.WriteLine("The Coach depends on the IPlayer abstraction, allowing for easy team changes!\n");

        // The composition root decides who plays today!
        var team = new List<IPlayer> 
        {
            new Forward(),
            new Midfielder(),
            new Winger() // Added a Winger without modifying the Coach!
        };

        var coach = new Coach(team);
        coach.ExecuteGamePlan();

        Console.WriteLine("\n===============================\n");
    }
}
using System;

namespace Chapter03.OCP.Before;

public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("=== Chapter 3: OCP (BEFORE) ===");
        Console.WriteLine("Midfielder uses hardcoded if/else logic for plays.\n");

        var midfielder = new Midfielder();
        midfielder.ExecutePlay("DribblePastOpponent");
        midfielder.ExecutePlay("DefensiveFormation");
        
        // This will fail because the class is closed to extension!
        midfielder.ExecutePlay("PassToStriker"); 

        Console.WriteLine("\n===============================\n");
    }
}
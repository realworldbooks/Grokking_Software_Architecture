using System;

namespace Chapter03.DIP.Before;

public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("=== Chapter 3: DIP (BEFORE) ===");
        Console.WriteLine("The Coach is tightly coupled to concrete players.\n");

        var coach = new Coach();
        coach.ExecuteGamePlan();

        Console.WriteLine("\n===============================\n");
    }
}
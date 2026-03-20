using System;

namespace Chapter03.ISP.After;

public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("=== Chapter 3: ISP (AFTER) ===");
        Console.WriteLine("Interfaces are segregated. No more NotImplementedExceptions!\n");

        IFieldPlayerTraining midfielder = new Midfielder();
        midfielder.PracticeShooting();
        
        Console.WriteLine();
        
        IGoalieTraining goalie = new Goalie();
        goalie.PracticeDivingSaves();
        goalie.PracticeHandDistribution();

        Console.WriteLine("\n===============================\n");
    }
}
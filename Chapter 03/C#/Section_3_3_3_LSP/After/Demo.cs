using System;

namespace Chapter03.LSP.After;

public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("=== Chapter 3: LSP (AFTER) ===");
        Console.WriteLine("Subclasses perfectly fulfill the parent contract!\n");

        var coach = new Coach();
        var midfielder = new Midfielder();
        var forward = new Forward();

        coach.DirectFieldPlay(midfielder);
        Console.WriteLine();
        coach.DirectFieldPlay(forward);

        Console.WriteLine("\n===============================\n");
    }
}
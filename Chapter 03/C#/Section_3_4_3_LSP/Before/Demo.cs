using System;

namespace Chapter03.LSP.Before;

public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("=== Chapter 3: LSP (BEFORE) ===");
        Console.WriteLine("Passing a Goalie as a generic Player breaks the contract!\n");

        var coach = new Coach();
        var goalie = new Goalie();

        // The Coach expects field play, but the Goalie breaks that expectation!
        coach.DirectFieldPlay(goalie);

        Console.WriteLine("\n===============================\n");
    }
}
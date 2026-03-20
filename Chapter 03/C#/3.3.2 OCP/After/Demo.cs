using System;

namespace Chapter03.OCP.After;

public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("=== Chapter 3: OCP (AFTER) ===");
        Console.WriteLine("Midfielder accepts any class implementing IPlay!\n");

        var midfielder = new Midfielder();
        
        midfielder.ExecutePlay(new DribblePastOpponent());
        midfielder.ExecutePlay(new DefensiveFormation());
        
        // We added a new play without modifying the Midfielder class!
        midfielder.ExecutePlay(new PassToStriker());

        Console.WriteLine("\n===============================\n");
    }
}
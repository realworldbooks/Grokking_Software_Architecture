using System;

namespace Chapter03.OCP.After;

public class DefensiveFormation : IPlay
{
    public void Execute()
    {
        Console.WriteLine("  [Action] Getting into defensive position…");
    }
}
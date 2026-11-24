using System;

namespace Chapter03.OCP.After;

/// <summary>
/// A concrete implementation of the IPlay interface.
/// </summary>
public class DefensiveFormation : IPlay
{
    public void Execute()
    {
        Console.WriteLine("  [Action] Getting into defensive position…");
    }
}
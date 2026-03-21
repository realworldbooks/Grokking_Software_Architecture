using System;

namespace Chapter03.OCP.After;

/// <summary>
/// A concrete implementation of the IPlay interface.
/// </summary>
public class DribblePastOpponent : IPlay
{
    public void Execute()
    {
        Console.WriteLine("  [Action] Executing a dribble move…");
    }
}
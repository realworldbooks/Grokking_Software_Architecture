using System;

namespace Chapter03.OCP.After;

public class DribblePastOpponent : IPlay
{
    public void Execute()
    {
        Console.WriteLine("  [Action] Executing a dribble move…");
    }
}
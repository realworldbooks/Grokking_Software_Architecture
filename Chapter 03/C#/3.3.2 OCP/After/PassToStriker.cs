using System;

namespace Chapter03.OCP.After;

public class PassToStriker : IPlay
{
    public void Execute()
    {
        Console.WriteLine("  [Action] Passing the ball to the striker!");
    }
}
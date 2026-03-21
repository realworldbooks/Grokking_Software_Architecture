using System;

namespace Chapter03.OCP.After;

/// <summary>
/// OCP SOLUTION: Open for Extension.
/// 
/// ARCHITECTURE NOTE: This class proves that our system is "Open for extension." 
/// We added this brand new feature (a new play) simply by creating a new file 
/// and implementing the IPlay interface. We extended the system's capabilities 
/// without touching any existing code!
/// </summary>
public class PassToStriker : IPlay
{
    public void Execute()
    {
        Console.WriteLine("  [Action] Passing the ball to the striker!");
    }
}
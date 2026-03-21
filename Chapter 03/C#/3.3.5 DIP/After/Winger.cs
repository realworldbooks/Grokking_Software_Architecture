using System;

namespace Chapter03.DIP.After;

/// <summary>
/// DIP SOLUTION: The "Plug-and-Play" Proof.
/// 
/// ARCHITECTURE NOTE: Because the Coach depends on an abstraction, we were 
/// able to create this brand new Winger class and instantly inject it into 
/// the Coach's game plan without altering a single line of the Coach class! 
/// 
/// This is the ultimate goal of software architecture: adding new features 
/// by adding new code, rather than rewriting existing code.
/// </summary>
public class Winger : IPlayer
{
    public void PerformAction() => Console.WriteLine("  [Action] Winger is running down the sideline.");
}
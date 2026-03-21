using System;

namespace Chapter03.DIP.After;
/// <summary>
/// A Low-Level Detail.
/// 
/// ARCHITECTURE NOTE: The concrete implementation now depends on the abstraction 
/// (IPlayer). The flow of dependency has been inverted.
/// </summary>
public class Forward : IPlayer
{
    public void PerformAction() => Console.WriteLine("  [Action] Forward is attacking.");
}
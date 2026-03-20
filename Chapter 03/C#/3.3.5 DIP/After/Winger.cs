using System;

namespace Chapter03.DIP.After;

public class Winger : IPlayer
{
    public void PerformAction() => Console.WriteLine("  [Action] Winger is running down the sideline.");
}
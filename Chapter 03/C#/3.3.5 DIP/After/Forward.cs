using System;

namespace Chapter03.DIP.After;

public class Forward : IPlayer
{
    public void PerformAction() => Console.WriteLine("  [Action] Forward is attacking.");
}
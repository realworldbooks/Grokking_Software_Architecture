using System;

namespace Chapter03.DIP.After;

public class Midfielder : IPlayer
{
    public void PerformAction() => Console.WriteLine("  [Action] Midfielder is controlling the game.");
}
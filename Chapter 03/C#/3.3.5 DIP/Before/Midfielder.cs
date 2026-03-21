using System;

namespace Chapter03.DIP.Before;

/// <summary>
/// A Low-Level Module.
/// 
/// ARCHITECTURE NOTE: Similar to the Forward, this concrete class is 
/// directly wired into the Coach. There is no abstraction, meaning the 
/// system is rigid and difficult to scale.
/// </summary>
public class Midfielder
{
    public void ControlMidfield() => Console.WriteLine("  [Action] Midfielder is controlling the game.");
}
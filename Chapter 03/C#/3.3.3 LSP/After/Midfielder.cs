using System;

namespace Chapter03.LSP.After;

public class Midfielder : Player 
{
    public override void PlayFieldPosition() 
    {
        Console.WriteLine("  [Midfielder] Controlling the midfield, passing and tackling."); 
    } 
}
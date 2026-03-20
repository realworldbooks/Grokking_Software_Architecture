using System;

namespace Chapter03.LSP.After;

public class Forward : Player 
{
    public override void PlayFieldPosition() 
    {
        Console.WriteLine("  [Forward] Leading the attack and trying to score."); 
    } 
}
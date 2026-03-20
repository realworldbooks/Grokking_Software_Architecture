using System;

namespace Chapter03.ISP.After;

public class Midfielder : IFieldPlayerTraining
{
    public void PracticeShooting() 
    { 
        Console.WriteLine("  [Midfielder] Practicing shooting drills."); 
    }
    
    public void PracticeTackling() 
    { 
        Console.WriteLine("  [Midfielder] Practicing slide tackles."); 
    }
}
using System;

namespace Chapter03.ISP.After;

// Goalies practice everything!
public class Goalie : IFieldPlayerTraining, IGoalieTraining
{
    public void PracticeShooting() 
    { 
        Console.WriteLine("  [Goalie] Practicing goal kicks and long shots."); 
    }
    
    public void PracticeTackling() 
    { 
        Console.WriteLine("  [Goalie] Practicing 1-on-1 box tackles."); 
    }
    
    public void PracticeDivingSaves() 
    { 
        Console.WriteLine("  [Goalie] Practicing top-corner diving saves."); 
    }
    
    public void PracticeHandDistribution() 
    { 
        Console.WriteLine("  [Goalie] Practicing fast break throws."); 
    }
}
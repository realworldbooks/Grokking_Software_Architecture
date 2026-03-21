using System;

namespace Chapter03.ISP.After;

/// <summary>
/// Interface Composition.
/// 
/// ARCHITECTURE NOTE: The beauty of Interface Segregation is that classes 
/// can just "opt-in" to the behaviors they actually need. Because the Goalie 
/// needs to practice everything, it simply implements BOTH interfaces. 
/// 
/// We accommodated the complex requirements of the Goalie without polluting 
/// the Midfielder's codebase!
/// </summary>
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
using System;

namespace Chapter03.ISP.Before;

public class Midfielder : ITrainingSession
{
    public void PracticeShooting() 
    { 
        Console.WriteLine("  [Midfielder] Practicing shooting drills."); 
    }
    
    public void PracticeTackling() 
    { 
        Console.WriteLine("  [Midfielder] Practicing slide tackles."); 
    }
    
    public void PracticeDivingSaves() 
    {
        throw new NotImplementedException("Midfielders don't play in the net!");
    }
    
    public void PracticeHandDistribution() 
    {
        throw new NotImplementedException("Midfielders can't use their hands!");
    }
}
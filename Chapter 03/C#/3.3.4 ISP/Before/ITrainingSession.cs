namespace Chapter03.ISP.Before;

/// <summary>
/// ANTI-PATTERN: The "Fat Interface" (ISP Violation).
/// 
/// ARCHITECTURE PROBLEM: The Interface Segregation Principle dictates that 
/// no client should be forced to depend on methods it does not use. 
/// 
/// This interface is bloated. It assumes that anyone attending a training 
/// session needs to practice BOTH field skills (shooting, tackling) AND 
/// goalie skills (diving, hand distribution). This creates a massive 
/// burden on any specialized player trying to implement it.
/// </summary>
public interface ITrainingSession
{
    void PracticeShooting();
    void PracticeTackling();
    
    // 🚨 ARCHITECTURE WARNING: These methods pollute the interface 
    // for 90% of the players on the team!
    void PracticeDivingSaves(); 
    void PracticeHandDistribution(); 
}
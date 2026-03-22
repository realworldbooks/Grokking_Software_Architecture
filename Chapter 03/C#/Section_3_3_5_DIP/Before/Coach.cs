namespace Chapter03.DIP.Before;

/// <summary>
/// ANTI-PATTERN: Violation of the Dependency Inversion Principle (DIP).
/// 
/// ARCHITECTURE PROBLEM: The Dependency Inversion Principle states that 
/// high-level modules (like this Coach) should not depend on low-level 
/// modules (like the concrete Forward and Midfielder classes). Both should 
/// depend on abstractions.
/// 
/// Here, the Coach is tightly coupled to specific classes. By using the 
/// 'new' keyword inside the constructor, the Coach is permanently welded 
/// to these exact implementations. If we want to test the Coach in isolation, 
/// or if we want to sub in a different type of Forward (like a substitute), 
/// we are completely blocked. We have to rip open the Coach class to make changes.
/// </summary>
public class Coach
{
    // 🚨 ARCHITECTURE WARNING: Tightly coupled to concrete classes!
    private Forward _forward;
    private Midfielder _midfielder;

    public Coach()
    {
        // 🚨 ARCHITECTURE WARNING: The 'new' keyword is glue. The Coach is 
        // taking on the responsibility of creating its own dependencies.
        _forward = new Forward();
        _midfielder = new Midfielder();
    }

    public void ExecuteGamePlan()
    {
        _forward.Attack();
        _midfielder.ControlMidfield();
    }
}
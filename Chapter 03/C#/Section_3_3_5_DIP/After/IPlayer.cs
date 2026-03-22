namespace Chapter03.DIP.After;

/// <summary>
/// The Abstraction.
/// 
/// ARCHITECTURE NOTE: This interface is the fulcrum of the Dependency Inversion 
/// Principle. Instead of the high-level Coach depending on the low-level players, 
/// BOTH the Coach and the players now depend on this shared abstraction. 
/// 
/// This creates a clean boundary. The details (how to attack, how to defend) 
/// are hidden behind this simple contract.
/// </summary>
public interface IPlayer
{
    void PerformAction();
}
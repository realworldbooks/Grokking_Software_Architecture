namespace Chapter03.ISP.After;

/// <summary>
/// ISP SOLUTION: Segregated Interfaces.
/// 
/// ARCHITECTURE NOTE: We broke the "Fat Interface" down into highly specific, 
/// role-based contracts. This interface now ONLY contains the methods that 
/// universally apply to anyone playing on the field. Clients are no longer 
/// forced to depend on methods they don't use.
/// </summary>
public interface IFieldPlayerTraining
{
    void PracticeShooting();
    void PracticeTackling();
}
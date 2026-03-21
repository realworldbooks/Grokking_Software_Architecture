namespace Chapter03.ISP.After;

/// <summary>
/// ISP SOLUTION: Segregated Interfaces.
/// 
/// ARCHITECTURE NOTE: This interface isolates the highly specialized skills 
/// required only by the Goalie. By keeping this separate, we protect the rest 
/// of the team from having to write dummy implementations for these methods.
/// </summary>
public interface IGoalieTraining
{
    void PracticeDivingSaves();
    void PracticeHandDistribution();
}
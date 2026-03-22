namespace Chapter03.OCP.After;

/// <summary>
/// OCP SOLUTION: Closed for Modification.
/// 
/// ARCHITECTURE NOTE: This class is now perfectly "Closed for modification." 
/// Notice that there are absolutely no if/else statements here. If the coach 
/// invents 100 new plays, we will NEVER have to open, edit, or recompile this 
/// Midfielder class. The risk of breaking existing functionality is zero!
/// </summary>
public class Midfielder
{
    /// <summary>
    /// Executes any play dynamically through polymorphism.
    /// </summary>
    /// <param name="play">Any class that implements the IPlay interface.</param>
    public void ExecutePlay(IPlay play)
    {
        // The Midfielder doesn't need to know WHAT the play is, 
        // it just knows HOW to execute it!
        play.Execute();
    }
}
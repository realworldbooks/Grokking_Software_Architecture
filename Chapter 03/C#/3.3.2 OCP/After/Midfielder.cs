namespace Chapter03.OCP.After;

public class Midfielder
{
    public void ExecutePlay(IPlay play)
    {
        // The Midfielder doesn't need to know WHAT the play is, 
        // it just knows HOW to execute it!
        play.Execute();
    }
}
namespace Chapter03.DIP.Before;

public class Coach
{
    // The Coach is tightly coupled to concrete classes!
    private Forward _forward;
    private Midfielder _midfielder;

    public Coach()
    {
        _forward = new Forward();
        _midfielder = new Midfielder();
    }

    public void ExecuteGamePlan()
    {
        _forward.Attack();
        _midfielder.ControlMidfield();
    }
}
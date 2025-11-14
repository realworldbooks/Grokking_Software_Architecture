namespace Ocp.GoodWay
{
    // Our Midfielder is now stable and never needs to change.
    public class Midfielder
    {
        public void ExecutePlay(IPlay play)
        {
            // This class is now open to any new IPlay we create.
            play.Execute();
        }
    }
}
namespace Dip.BadWay
{
    // Low-level modules (concrete players)
    public class Forward
    {
        public void Attack() => Console.WriteLine("Forward is attacking.");
    }
    public class Midfielder
    {
        public void ControlMidfield() => Console.WriteLine("Midfielder is controlling the game.");
    }

    // High-level module (the Coach)
    public class Coach
    {
        private Forward _forward;
        private Midfielder _midfielder;
        public Coach()
        {
            // The high-level class depends on concrete low-level classes
            _forward = new Forward();
            _midfielder = new Midfielder();
        }
        public void ExecuteGamePlan()
        {
            _forward.Attack();
            _midfielder.ControlMidfield();
        }
    }
}
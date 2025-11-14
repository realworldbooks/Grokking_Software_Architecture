namespace Isp.GoodWay
{
    public class Midfielder : IFieldPlayerTraining
    {
        public void PracticeShooting() { Console.WriteLine("Midfielder: Practicing shooting..."); }
        public void PracticeTackling() { Console.WriteLine("Midfielder: Practicing tackling..."); }
    }

    public class Goalie : IFieldPlayerTraining, IGoalieTraining
    {
        public void PracticeShooting() { Console.WriteLine("Goalie: Practicing shooting..."); }
        public void PracticeTackling() { Console.WriteLine("Goalie: Practicing tackling..."); }
        public void PracticeDivingSaves() { Console.WriteLine("Goalie: Practicing diving saves..."); }
        public void PracticeHandDistribution() { Console.WriteLine("Goalie: Practicing hand distribution..."); }
    }
}
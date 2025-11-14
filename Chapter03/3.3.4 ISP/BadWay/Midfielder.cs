namespace Isp.BadWay
{
    public class Midfielder : ITrainingSession
    {
        public void PracticeShooting() { Console.WriteLine("Practicing shooting..."); }
        public void PracticeTackling() { Console.WriteLine("Practicing tackling..."); }

        public void PracticeDivingSaves()
        {
            throw new NotImplementedException("I'm a midfielder, not a goalie!");
        }
        public void PracticeHandDistribution()
        {
            throw new NotImplementedException("I'm a midfielder, not a goalie!");
        }
    }
}
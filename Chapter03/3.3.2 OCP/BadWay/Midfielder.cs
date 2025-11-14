namespace Ocp.BadWay
{
    public class Midfielder
    {
        public void ExecutePlay(string playName)
        {
            if (playName == "DribblePastOpponent")
            {
                Console.WriteLine("Executing a dribble move...");
                // Dribbling logic here
            }
            else if (playName == "DefensiveFormation")
            {
                Console.WriteLine("Getting into defensive position...");
                // Defensive logic here
            }
            // To add a new play, we MUST add another "else if" here.
        }
    }
}
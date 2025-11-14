using System;

namespace Isp.BadWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var midfield = new Midfielder();
            midfield.PracticeShooting();
            midfield.PracticeTackling();
            Console.WriteLine("Midfielder finished relevant drills.");
            try
            {
                Console.WriteLine("\nTrying to force midfielder to do goalie drills...");
                midfield.PracticeDivingSaves();
            }
            catch (NotImplementedException e)
            {
                Console.WriteLine($"Caught error: {e.Message}");
            }
        }
    }
}
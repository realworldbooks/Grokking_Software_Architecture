using System;

namespace Isp.GoodWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var midfield = new Midfielder();
            midfield.PracticeShooting();
            midfield.PracticeTackling();
            // midfield.PracticeDivingSaves(); // <-- This won't even compile!
            Console.WriteLine("Midfielder practices complete.");

            Console.WriteLine("\n---");
            var goalie = new Goalie();
            goalie.PracticeShooting();
            goalie.PracticeDivingSaves();
            Console.WriteLine("Goalie practices complete.");
        }
    }
}
using System;

namespace Ocp.After
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var midfielder = new Midfielder();
            Console.WriteLine("Executing After - Using OCP:");
            midfielder.ExecutePlay(new DribblePastOpponent());
            midfielder.ExecutePlay(new DefensiveFormation());
            // To add a new play, we just create a new class!
        }
    }
}
using System;

namespace Ocp.Before
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var midfielder = new Midfielder();
            Console.WriteLine("Executing Before - Not Using OCP:");
            midfielder.ExecutePlay("DribblePastOpponent");
            midfielder.ExecutePlay("DefensiveFormation");
        }
    }
}
using System;

namespace Ocp.BadWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var midfielder = new Midfielder();
            Console.WriteLine("Executing Bad Way OCP:");
            midfielder.ExecutePlay("DribblePastOpponent");
            midfielder.ExecutePlay("DefensiveFormation");
        }
    }
}
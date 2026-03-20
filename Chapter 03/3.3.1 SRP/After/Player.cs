namespace Srp.After
{
    // Responsibility 1: Manages only the player’s state and actions
    public class Player
    {
        public string Name { get; set; }
        public void DribbleBall() { Console.WriteLine("Dribbling ball..."); }
    }
}
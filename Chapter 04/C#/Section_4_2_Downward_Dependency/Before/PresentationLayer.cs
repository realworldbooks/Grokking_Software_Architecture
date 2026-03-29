namespace Chapter04.Section_4_2.Before
{
    // A fake UI layer class to illustrate the bad dependency
    public class PresentationLayer
    {
        public static PresentationLayer Instance { get; } = new PresentationLayer();
        public void UpdateStatusLabel(string text)
        {
            Console.WriteLine($"[UI UPDATE]: {text}");
        }
    }
}
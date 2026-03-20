namespace Before
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
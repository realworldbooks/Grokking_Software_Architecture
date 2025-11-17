namespace BadWay
{
    // DataAccessLayer.cs 
    public class SomeRepository 
    {
        // This is the violation! A lower layer should not
        // know about an upper layer.
        private PresentationLayer _uiLayer = PresentationLayer.Instance;

        public void UpdateData(int id, string newData)
        {
            Console.WriteLine("(BAD) Saving data to database...");
            
            // VIOLATION! Calling upwards to the UI Layer
            _uiLayer.UpdateStatusLabel($"(BAD) Data {id} Saved!");
        }
    }
}
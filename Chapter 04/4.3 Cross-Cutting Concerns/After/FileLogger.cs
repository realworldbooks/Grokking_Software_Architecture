namespace After
    // A concrete implementation of the contract
    public class FileLogger : ILogger
    {
        public void Log(string message)
        {
            Console.WriteLine($"(AFTER_LOGGER) File Log: {message}");
        }
    }
}
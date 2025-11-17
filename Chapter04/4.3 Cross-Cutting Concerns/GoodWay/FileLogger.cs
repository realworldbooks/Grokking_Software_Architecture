namespace GoodWay
{
    // A concrete implementation of the contract
    public class FileLogger : ILogger
    {
        public void Log(string message)
        {
            Console.WriteLine($"(GOOD_LOGGER) File Log: {message}");
        }
    }
}
namespace BadWay
{
    // The problematic static logger
    public static class StaticFileLogger
    {
        public static void Log(string message)
        {
            Console.WriteLine($"(BAD_LOGGER) Static Log: {message}");
        }
    }
}
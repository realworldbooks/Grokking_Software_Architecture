namespace Chapter04.Section_4_3.Before
{
    // The problematic static logger
    public static class StaticFileLogger
    {
        public static void Log(string message)
        {
            Console.WriteLine($"(BEFORE_LOGGER) Static Log: {message}");
        }
    }
}
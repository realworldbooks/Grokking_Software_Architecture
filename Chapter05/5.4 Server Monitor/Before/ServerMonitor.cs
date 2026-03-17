using System;

namespace ServerMonitor.Before
{
    // THE CORE (Tightly Coupled)
    public class ServerMonitor
    {
        public void CheckTemperature(int temp)
        {
            // VIOLATION: Hardcoded check inside the logic
            if (temp > 95)
            {
                // VIOLATION: We are hardcoding a dependency on a specific external tool (Twilio).
                // We can't test this logic without actually sending a text message!
                var twilio = new TwilioClient("API_KEY");
                twilio.SendSms("555-1234", "Server is overheating!");
            }
            else
            {
                Console.WriteLine($"Temp {temp} is nominal.");
            }
        }
    }

    // FAKE 3RD PARTY LIBRARY (To make code compile)
    public class TwilioClient
    {
        public TwilioClient(string key) { }
        public void SendSms(string to, string body) 
        {
            Console.WriteLine($"[Twilio API] Sending SMS to {to}: {body}");
        }
    }

    // PROGRAM ENTRY
    class Program
    {
        static void Main(string[] args)
        {
            var monitor = new ServerMonitor();
            monitor.CheckTemperature(95); // This will actually try to call the API
        }
    }
}

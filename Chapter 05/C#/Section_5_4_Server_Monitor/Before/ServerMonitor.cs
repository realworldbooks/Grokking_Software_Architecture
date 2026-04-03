using System;

namespace Chapter05.ServerMonitor.Before
{
    /// <summary>
    /// The Core Business Logic.
    /// WARNING: This class is a "Liability" because it violates the 
    /// Golden Rule of Separation of Concerns.
    /// </summary>
    public class ServerMonitor
    {
        /// <summary>
        /// Checks the server temperature and sends an alert if it's too high.
        /// </summary>
        /// <param name="temp">The current temperature reading.</param>
        public void CheckTemperature(int temp)
        {
            // VIOLATION: Hardcoded "magic number". 
            // This should be a configurable threshold.
            if (temp > 95)
            {
                // VIOLATION: Tight Coupling.
                // We are 'new-ing' up a concrete dependency inside our logic.
                // This makes the class impossible to unit test without a live API.
                var twilio = new TwilioClient("API_KEY");
                twilio.SendSms("555-1234", "Server is overheating!");
            }
            else
            {
                Console.WriteLine($"Temp {temp} is nominal.");
            }
        }
    }

    /// <summary>
    /// Mock of a 3rd party SMS library.
    /// In a real system, this is the "Chaotic Outside World".
    /// </summary>
    public class TwilioClient
    {
        public TwilioClient(string key) { }
        public void SendSms(string to, string body) 
        {
            Console.WriteLine($"[Twilio API] Sending SMS to {to}: {body}");
        }
    }
}
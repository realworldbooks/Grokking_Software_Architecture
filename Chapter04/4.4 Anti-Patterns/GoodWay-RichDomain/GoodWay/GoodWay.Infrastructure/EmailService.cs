using GoodWay.Application;

namespace GoodWay.Infrastructure
{
    // Concrete implementation for an email provider
    public class SmtpEmailService : IEmailService
    {
        public void Send(string to, string subject, string body)
        {
            Console.WriteLine($"(INFRA) Sending email to {to}...");
        }
    }
}
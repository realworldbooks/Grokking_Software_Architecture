using After.DomainModels;

namespace After.DataAccess
{
    /// <summary>
    /// ARCHITECTURE NOTE: By isolating Email logic here, we prevent 
    /// database concerns from "leaking" into the Presentation or 
    /// Business layers.
    /// </summary>
    // Concrete implementation for an email provider
   public class SmtpEmailService : IEmailService
    {
        public void Send(string to, string sub, string body) { }
    }
}
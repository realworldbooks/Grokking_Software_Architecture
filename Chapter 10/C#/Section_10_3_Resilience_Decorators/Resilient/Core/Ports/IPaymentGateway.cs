using System.Threading.Tasks;

namespace Chapter10.Resilience.Core.Ports;

/// <summary>
/// THE CORE PORT (The Primary Airlock):
/// 
/// DESIGN NOTE:
/// This interface defines the NEED of the business. The Core does not know
/// about 'Polly', 'HttpClient', or 'Exponential Backoff'. It only knows
/// that it needs to charge a card and receive a boolean success result.
/// </summary>
public interface IPaymentGateway
{
    Task<bool> ChargeAsync(decimal amount, string orderId, string idempotencyKey);
}
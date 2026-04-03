using System.Threading.Tasks;

namespace Chapter05.CryptoTracker.After.Core.Ports
{
    /// <summary>
    /// PORT – Defines "What" we need (lives in Core).
    /// This is the "Socket." It dictates the contract the outside world must follow.
    /// </summary>
    public interface IPriceProviderPort
    {
        Task<decimal> GetBitcoinPrice();
    }
}
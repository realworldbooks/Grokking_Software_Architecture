using System.Threading.Tasks;

namespace CryptoTracker.After
{
    // PORT – Defines "What" we need (lives in Core)
    public interface IPriceProviderPort
    {
        Task<decimal> GetBitcoinPrice();
    }
}
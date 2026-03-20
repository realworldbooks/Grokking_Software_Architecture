using System.Threading.Tasks;
using CryptoTracker.After.Core.Ports;

namespace CryptoTracker.After.Infrastructure.Adapters
{
    // ADAPTER 1: The "Airplane Mode" Adapter
    // Always returns $50,000. No internet needed!
    public class FakePriceProvider : IPriceProviderPort
    {
        public Task<decimal> GetBitcoinPrice() => Task.FromResult(50000m);
    }
}

using System.Threading.Tasks;
using Chapter05.CryptoTracker.After.Core.Ports;

namespace Chapter05.CryptoTracker.After.Infrastructure.Adapters
{
    /// <summary>
    /// ADAPTER 1: The "Airplane Mode" / Test Adapter.
    /// Proves that we can run and test the system without an internet connection.
    /// </summary>
    public class FakePriceProvider : IPriceProviderPort
    {
        private readonly decimal _fixedPrice;

        public FakePriceProvider(decimal fixedPrice = 50_000m)
        {
            _fixedPrice = fixedPrice;
        }

        public Task<decimal> GetBitcoinPrice() => Task.FromResult(_fixedPrice);
    }
}
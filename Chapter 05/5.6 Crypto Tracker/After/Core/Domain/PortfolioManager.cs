using System.Threading.Tasks;

namespace CryptoTracker.After
{
    // CORE – Pure business logic
    public class PortfolioManager
    {
        private readonly IPriceProviderPort _priceProvider;

        // Dependency Injection via Constructor. We demand a “socket”, but we don’t care which one!
        public PortfolioManager(IPriceProviderPort priceProvider)
        {
            _priceProvider = priceProvider;
        }

        public async Task<decimal> CalculateTotalValue(decimal btcAmount)
        {
            // We just call the port. We don't care WHERE the price comes from.
            var currentPrice = await _priceProvider.GetBitcoinPrice();
            // Pure math. No JSON parsing here!
            return btcAmount * currentPrice;
        }
    }
}

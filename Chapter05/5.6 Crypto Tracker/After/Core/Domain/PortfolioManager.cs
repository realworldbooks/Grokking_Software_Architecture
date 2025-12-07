using System.Threading.Tasks;

namespace CryptoTracker.After
{
    // CORE – Pure business logic
    public class PortfolioManager
    {
        private readonly IPriceProviderPort _priceProvider;

        public PortfolioManager(IPriceProviderPort priceProvider)
        {
            _priceProvider = priceProvider;
        }

        public async Task<decimal> CalculateTotalValue(decimal btcAmount)
        {
            // We just call the port. We don't care WHERE the price comes from.
            var currentPrice = await _priceProvider.GetBitcoinPrice();
            return btcAmount * currentPrice;
        }
    }
}
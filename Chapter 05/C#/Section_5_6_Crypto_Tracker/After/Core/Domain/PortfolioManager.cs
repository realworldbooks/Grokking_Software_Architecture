using System.Threading.Tasks;
using Chapter05.CryptoTracker.After.Core.Ports;

namespace Chapter05.CryptoTracker.After.Core.Domain
{
    /// <summary>
    /// CORE – Pure business logic.
    /// No HTTP clients, no JSON parsing. This class is fully isolated.
    /// </summary>
    public class PortfolioManager
    {
        private readonly IPriceProviderPort _priceProvider;

        /// <summary>
        /// Dependency Injection via Constructor. 
        /// We demand a "socket" (IPriceProviderPort), but we don't care which 
        /// plug (Adapter) the Boundary Keeper decides to use!
        /// </summary>
        public PortfolioManager(IPriceProviderPort priceProvider)
        {
            _priceProvider = priceProvider;
        }

        public async Task<decimal> CalculateTotalValue(decimal btcAmount)
        {
            // We just call the port. We don't care WHERE the price comes from.
            var currentPrice = await _priceProvider.GetBitcoinPrice();
            
            // Pure math. No network dependencies here!
            return btcAmount * currentPrice;
        }
    }
}
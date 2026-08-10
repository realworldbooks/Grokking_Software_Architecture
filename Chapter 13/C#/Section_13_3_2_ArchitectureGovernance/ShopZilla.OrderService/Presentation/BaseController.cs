using Microsoft.AspNetCore.Mvc;

namespace ShopZilla.OrderService.Presentation
{
    /// <summary>
    /// The base class for all API controllers in the Presentation layer.
    ///
    /// ARCHITECTURAL RULE: Every controller in the system must inherit
    /// from this class AND follow the naming convention of ending with
    /// the suffix "Controller". Our fitness function (Listing 13.1)
    /// enforces both rules automatically in the CI pipeline.
    ///
    /// If a developer creates a new controller that forgets the suffix,
    /// or places it outside the Presentation namespace, the build fails.
    /// </summary>
    [ApiController]
    public abstract class BaseController : ControllerBase
    {
        /// <summary>
        /// A shared helper that all controllers can use to wrap responses.
        /// </summary>
        protected OkObjectResult OkResult(object data) => Ok(data);
    }
}